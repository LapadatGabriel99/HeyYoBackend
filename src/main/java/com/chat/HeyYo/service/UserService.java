package com.chat.HeyYo.service;

import com.chat.HeyYo.document.Complaint;
import com.chat.HeyYo.document.ERole;
import com.chat.HeyYo.document.Role;
import com.chat.HeyYo.document.User;
import com.chat.HeyYo.exception.ConfirmationTokenException;
import com.chat.HeyYo.exception.EmailNotFoundException;
import com.chat.HeyYo.exception.RoleNotFoundException;
import com.chat.HeyYo.exception.UserNotFoundException;
import com.chat.HeyYo.repository.RoleRepository;
import com.chat.HeyYo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    @Autowired
    private ComplaintService complaintService;

    public User login(User user, HttpServletResponse response) {

        return authenticationService.authenticateUser(user, response)
                .orElseThrow(() -> new UserNotFoundException(user.getUsername()));
    }

    public User register(User user) throws MessagingException {

        user.setRegistrationDate(LocalDateTime.now());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(manageRoles(user.getRoles()));
        user.setLocked(false);

        var repoUser = userRepository.save(user);

        var token = userDetailsService.handleConfirmationToken(repoUser);

        var link = "http://localhost:8080/api/v1.0/user/confirm?token=" + token;

        emailService.send(repoUser.getEmail(), buildEmail( repoUser.getUsername(),
                "Confirm your email",
                " Thank you for registering. Please click on the below link to activate your account: ",
                link,
                "Activate Now",
                "\n Link will expire in 15 minutes. ",
                "See you soon"));

        return repoUser;
    }

    public void logout(HttpServletResponse response) {

        authenticationService.removeCurrentJwtTokenAuthentication(response);
    }

    public String confirmToken(String token, boolean isPasswordRecoveryToken) {

        var confirmationToken = confirmationTokenService.getToken(token)
                .orElseThrow(() -> new ConfirmationTokenException("Token with value %s not found!", token));

        if (confirmationToken.getConfirmedAt() != null) {

            throw new ConfirmationTokenException("Email already confirmed!");
        }

        var expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {

            throw new ConfirmationTokenException("Token expired!");
        }

        confirmationTokenService.setConfirmedAt(token);

        if (!isPasswordRecoveryToken) {

            userDetailsService.enableUser(confirmationToken.getUser().getEmail());
        }

        return "confirmed";
    }

    public String sendPasswordRecoveryMail(String email) throws MessagingException {

        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EmailNotFoundException(email));

        var token = userDetailsService.handleConfirmationToken(user);

        emailService.send(email, buildEmail(user.getUsername(),
                "Password recovery",
                "Glad you contacted us. Please use the token below to reset your password: ",
                "",
                "Reset Now: " + token,
                "\n Token will expire in 15 minutes. ",
                "Good lock to you"));

        return user.getUsername();
    }

    public void updatePassword(User user) {

        userDetailsService.updatePassword(user.getUsername(),
                bCryptPasswordEncoder.encode(user.getPassword()));
    }

    public void lockUserAccount(String username) {

        userDetailsService.lockUser(username);
    }

    private Set<Role> manageRoles(Set<Role> roles) {

        if (roles == null) {

            var userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RoleNotFoundException(ERole.ROLE_USER));

            roles = new HashSet<>();
            roles.add(userRole);

            return roles;
        }

        return roles;
    }

    public void reportUser(Complaint complaint) {

        complaintService.registerComplaint(complaint);
    }

    private String buildEmail(String name,
                              String title,
                              String textStrip1,
                              String link,
                              String textStrip2,
                              String textStrip3,
                              String textStrip4) {

        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">" +  title + "</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">" + textStrip1 + "</p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">" + textStrip2 +"</a> </p></blockquote>"+ textStrip3 +"<p>" + textStrip4 + "</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }
}
