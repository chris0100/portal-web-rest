package com.portalweb.services;


import com.portalweb.controllers.EmailController;
import com.portalweb.models.Usuario;
import com.portalweb.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService implements UsuarioServices,UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepositoryObj;

    EmailController emailController;


    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException,IndexOutOfBoundsException {
        System.out.println(username + "username ing");


        Usuario usuario = usuarioRepositoryObj.findAllByUsername(username).get(0);
        //Usuario usuario = usuarioRepositoryObj.findByNit()

        if (usuario != null) {
            System.out.println("datos usuario: " + usuario.getUsername() + ": " + usuario.getNombre() + ": " + usuario.getPassword() + ":" + usuario.getId());
        }

        if (usuario == null){
            System.out.println("Usuario no existe");
            throw new UsernameNotFoundException("Error: no existe el usuario");
        }


        //Se carga la lista de roles.
        List<GrantedAuthority> authorities = usuario.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getId().trim()))
                .peek(authority -> System.out.println("role: " + authority.getAuthority()))
                .collect(Collectors.toList());

        return new User(usuario.getUsername().trim(), usuario.getPassword().trim(), true, true,
                true, true, authorities);
    }

    
    @Override
    @Transactional(readOnly = true)
    public Usuario findByUsername(String username) {
        return usuarioRepositoryObj.findAllByUsername(username).get(0);
    }


    @Override
    public Usuario findById(Long id) {
        return usuarioRepositoryObj.findById(id).get();
    }

    @Override
    public List<Usuario> findAll() {
        return (List<Usuario>) usuarioRepositoryObj.findAll();
    }

    @Override
    public Usuario save(Usuario usuario) {
        return usuarioRepositoryObj.save(usuario);
    }

    @Override
    public List<Usuario> findAllByEmailAndNit(String email, String nit) {
        return usuarioRepositoryObj.findAllByEmailAndNit(email, nit);
    }


    @Override
    public Usuario findByToken(String token) {
        return usuarioRepositoryObj.findByToken(token);
    }

    @Override
    public String obtenerRazonSocialUsuarioNuevo(String nit, String password) {
        return usuarioRepositoryObj.obtenerRazonSocial(nit, password);
    }


    @Override
    public Usuario findByValidarUsername(String username) {
        return usuarioRepositoryObj.findByUsername(username);
    }


    @Override
    public Usuario findByCorreoRecuperarContra(String email, String nit, String sucursal, String puntoEnvio) {
        return usuarioRepositoryObj.findByEmailAndNitAndIdSucursalAndIdPuntoEnvio(email, nit, sucursal, puntoEnvio);
    }


    @Override
    public String nitContraseñaUsuarioNuevoProveedor(String nit, String password) {
        return usuarioRepositoryObj.obtenerRazonSocialProveedorNuevo(nit, password);
    }

    @Override
    public void sendMailRecuperarContrasena(Usuario usuario) throws FileNotFoundException {
        emailController = new EmailController();
        String asunto = "Solicitud de restablecimiento de contraseña";
        String cuerpo = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional //EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\"><head>\n" +
                "    <!--[if gte mso 9]><xml>\n" +
                "     <o:OfficeDocumentSettings>\n" +
                "      <o:AllowPNG/>\n" +
                "      <o:PixelsPerInch>96</o:PixelsPerInch>\n" +
                "     </o:OfficeDocumentSettings>\n" +
                "    </xml><![endif]-->\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width\">\n" +
                "    <!--[if !mso]><!--><meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"><!--<![endif]-->\n" +
                "    <title></title>\n" +
                "    <!--[if !mso]><!-- -->\n" +
                "	<link href=\"https://fonts.googleapis.com/css?family=Open+Sans\" rel=\"stylesheet\" type=\"text/css\">\n" +
                "	<!--<![endif]-->\n" +
                "    \n" +
                "    <style type=\"text/css\" id=\"media-query\">\n" +
                "      body {\n" +
                "  margin: 0;\n" +
                "  padding: 0; }\n" +
                "\n" +
                "table, tr, td {\n" +
                "  vertical-align: top;\n" +
                "  border-collapse: collapse; }\n" +
                "\n" +
                ".ie-browser table, .mso-container table {\n" +
                "  table-layout: fixed; }\n" +
                "\n" +
                "* {\n" +
                "  line-height: inherit; }\n" +
                "\n" +
                "a[x-apple-data-detectors=true] {\n" +
                "  color: inherit !important;\n" +
                "  text-decoration: none !important; }\n" +
                "\n" +
                "[owa] .img-container div, [owa] .img-container button {\n" +
                "  display: block !important; }\n" +
                "\n" +
                "[owa] .fullwidth button {\n" +
                "  width: 100% !important; }\n" +
                "\n" +
                "[owa] .block-grid .col {\n" +
                "  display: table-cell;\n" +
                "  float: none !important;\n" +
                "  vertical-align: top; }\n" +
                "\n" +
                ".ie-browser .num12, .ie-browser .block-grid, [owa] .num12, [owa] .block-grid {\n" +
                "  width: 600px !important; }\n" +
                "\n" +
                ".ExternalClass, .ExternalClass p, .ExternalClass span, .ExternalClass font, .ExternalClass td, .ExternalClass div {\n" +
                "  line-height: 100%; }\n" +
                "\n" +
                ".ie-browser .mixed-two-up .num4, [owa] .mixed-two-up .num4 {\n" +
                "  width: 200px !important; }\n" +
                "\n" +
                ".ie-browser .mixed-two-up .num8, [owa] .mixed-two-up .num8 {\n" +
                "  width: 400px !important; }\n" +
                "\n" +
                ".ie-browser .block-grid.two-up .col, [owa] .block-grid.two-up .col {\n" +
                "  width: 300px !important; }\n" +
                "\n" +
                ".ie-browser .block-grid.three-up .col, [owa] .block-grid.three-up .col {\n" +
                "  width: 200px !important; }\n" +
                "\n" +
                ".ie-browser .block-grid.four-up .col, [owa] .block-grid.four-up .col {\n" +
                "  width: 150px !important; }\n" +
                "\n" +
                ".ie-browser .block-grid.five-up .col, [owa] .block-grid.five-up .col {\n" +
                "  width: 120px !important; }\n" +
                "\n" +
                ".ie-browser .block-grid.six-up .col, [owa] .block-grid.six-up .col {\n" +
                "  width: 100px !important; }\n" +
                "\n" +
                ".ie-browser .block-grid.seven-up .col, [owa] .block-grid.seven-up .col {\n" +
                "  width: 85px !important; }\n" +
                "\n" +
                ".ie-browser .block-grid.eight-up .col, [owa] .block-grid.eight-up .col {\n" +
                "  width: 75px !important; }\n" +
                "\n" +
                ".ie-browser .block-grid.nine-up .col, [owa] .block-grid.nine-up .col {\n" +
                "  width: 66px !important; }\n" +
                "\n" +
                ".ie-browser .block-grid.ten-up .col, [owa] .block-grid.ten-up .col {\n" +
                "  width: 60px !important; }\n" +
                "\n" +
                ".ie-browser .block-grid.eleven-up .col, [owa] .block-grid.eleven-up .col {\n" +
                "  width: 54px !important; }\n" +
                "\n" +
                ".ie-browser .block-grid.twelve-up .col, [owa] .block-grid.twelve-up .col {\n" +
                "  width: 50px !important; }\n" +
                "\n" +
                "@media only screen and (min-width: 620px) {\n" +
                "  .block-grid {\n" +
                "    width: 600px !important; }\n" +
                "  .block-grid .col {\n" +
                "    vertical-align: top; }\n" +
                "    .block-grid .col.num12 {\n" +
                "      width: 600px !important; }\n" +
                "  .block-grid.mixed-two-up .col.num4 {\n" +
                "    width: 200px !important; }\n" +
                "  .block-grid.mixed-two-up .col.num8 {\n" +
                "    width: 400px !important; }\n" +
                "  .block-grid.two-up .col {\n" +
                "    width: 300px !important; }\n" +
                "  .block-grid.three-up .col {\n" +
                "    width: 200px !important; }\n" +
                "  .block-grid.four-up .col {\n" +
                "    width: 150px !important; }\n" +
                "  .block-grid.five-up .col {\n" +
                "    width: 120px !important; }\n" +
                "  .block-grid.six-up .col {\n" +
                "    width: 100px !important; }\n" +
                "  .block-grid.seven-up .col {\n" +
                "    width: 85px !important; }\n" +
                "  .block-grid.eight-up .col {\n" +
                "    width: 75px !important; }\n" +
                "  .block-grid.nine-up .col {\n" +
                "    width: 66px !important; }\n" +
                "  .block-grid.ten-up .col {\n" +
                "    width: 60px !important; }\n" +
                "  .block-grid.eleven-up .col {\n" +
                "    width: 54px !important; }\n" +
                "  .block-grid.twelve-up .col {\n" +
                "    width: 50px !important; } }\n" +
                "\n" +
                "@media (max-width: 620px) {\n" +
                "  .block-grid, .col {\n" +
                "    min-width: 320px !important;\n" +
                "    max-width: 100% !important;\n" +
                "    display: block !important; }\n" +
                "  .block-grid {\n" +
                "    width: calc(100% - 40px) !important; }\n" +
                "  .col {\n" +
                "    width: 100% !important; }\n" +
                "    .col > div {\n" +
                "      margin: 0 auto; }\n" +
                "  img.fullwidth, img.fullwidthOnMobile {\n" +
                "    max-width: 100% !important; }\n" +
                "  .no-stack .col {\n" +
                "    min-width: 0 !important;\n" +
                "    display: table-cell !important; }\n" +
                "  .no-stack.two-up .col {\n" +
                "    width: 50% !important; }\n" +
                "  .no-stack.mixed-two-up .col.num4 {\n" +
                "    width: 33% !important; }\n" +
                "  .no-stack.mixed-two-up .col.num8 {\n" +
                "    width: 66% !important; }\n" +
                "  .no-stack.three-up .col.num4 {\n" +
                "    width: 33% !important; }\n" +
                "  .no-stack.four-up .col.num3 {\n" +
                "    width: 25% !important; }\n" +
                "  .mobile_hide {\n" +
                "    min-height: 0px;\n" +
                "    max-height: 0px;\n" +
                "    max-width: 0px;\n" +
                "    display: none;\n" +
                "    overflow: hidden;\n" +
                "    font-size: 0px; } }\n" +
                "\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body class=\"clean-body\" style=\"margin: 0;padding: 0;-webkit-text-size-adjust: 100%;background-color: #B8CCE2\">\n" +
                "  <style type=\"text/css\" id=\"media-query-bodytag\">\n" +
                "    @media (max-width: 520px) {\n" +
                "      .block-grid {\n" +
                "        min-width: 320px!important;\n" +
                "        max-width: 100%!important;\n" +
                "        width: 100%!important;\n" +
                "        display: block!important;\n" +
                "      }\n" +
                "\n" +
                "      .col {\n" +
                "        min-width: 320px!important;\n" +
                "        max-width: 100%!important;\n" +
                "        width: 100%!important;\n" +
                "        display: block!important;\n" +
                "      }\n" +
                "\n" +
                "        .col > div {\n" +
                "          margin: 0 auto;\n" +
                "        }\n" +
                "\n" +
                "      img.fullwidth {\n" +
                "        max-width: 100%!important;\n" +
                "      }\n" +
                "			img.fullwidthOnMobile {\n" +
                "        max-width: 100%!important;\n" +
                "      }\n" +
                "      .no-stack .col {\n" +
                "				min-width: 0!important;\n" +
                "				display: table-cell!important;\n" +
                "			}\n" +
                "			.no-stack.two-up .col {\n" +
                "				width: 50%!important;\n" +
                "			}\n" +
                "			.no-stack.mixed-two-up .col.num4 {\n" +
                "				width: 33%!important;\n" +
                "			}\n" +
                "			.no-stack.mixed-two-up .col.num8 {\n" +
                "				width: 66%!important;\n" +
                "			}\n" +
                "			.no-stack.three-up .col.num4 {\n" +
                "				width: 33%!important;\n" +
                "			}\n" +
                "			.no-stack.four-up .col.num3 {\n" +
                "				width: 25%!important;\n" +
                "			}\n" +
                "      .mobile_hide {\n" +
                "        min-height: 0px!important;\n" +
                "        max-height: 0px!important;\n" +
                "        max-width: 0px!important;\n" +
                "        display: none!important;\n" +
                "        overflow: hidden!important;\n" +
                "        font-size: 0px!important;\n" +
                "      }\n" +
                "    }\n" +
                "  </style>\n" +
                "  <!--[if IE]><div class=\"ie-browser\"><![endif]-->\n" +
                "  <!--[if mso]><div class=\"mso-container\"><![endif]-->\n" +
                "  <table class=\"nl-container\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;min-width: 320px;Margin: 0 auto;background-color: #B8CCE2;width: 100%\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "	<tbody>\n" +
                "	<tr style=\"vertical-align: top\">\n" +
                "		<td style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top\">\n" +
                "    <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td align=\"center\" style=\"background-color: #B8CCE2;\"><![endif]-->\n" +
                "\n" +
                "    <div style=\"background-color:transparent;\">\n" +
                "      <div style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #FFFFFF;\" class=\"block-grid \">\n" +
                "        <div style=\"border-collapse: collapse;display: table;width: 100%;background-color:#FFFFFF;\">\n" +
                "          <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"background-color:transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width: 600px;\"><tr class=\"layout-full-width\" style=\"background-color:#FFFFFF;\"><![endif]-->\n" +
                "\n" +
                "              <!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\" width:600px; padding-right: 0px; padding-left: 0px; padding-top:0px; padding-bottom:0px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><![endif]-->\n" +
                "            <div class=\"col num12\" style=\"min-width: 320px;max-width: 600px;display: table-cell;vertical-align: top;\">\n" +
                "              <div style=\"background-color: transparent; width: 100% !important;\">\n" +
                "              <!--[if (!mso)&(!IE)]><!--><div style=\"border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent; padding-top:0px; padding-bottom:0px; padding-right: 0px; padding-left: 0px;\"><!--<![endif]-->\n" +
                "\n" +
                "                  \n" +
                "                    \n" +
                "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" class=\"divider mobile_hide\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;min-width: 100%;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\n" +
                "    <tbody>\n" +
                "        <tr style=\"vertical-align: top\">\n" +
                "            <td class=\"divider_inner\" style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top;padding-right: 5px;padding-left: 5px;padding-top: 5px;padding-bottom: 5px;min-width: 100%;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\n" +
                "                <table class=\"divider_content\" height=\"40px\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;border-top: 0px solid transparent;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\n" +
                "                    <tbody>\n" +
                "                        <tr style=\"vertical-align: top\">\n" +
                "                            <td style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top;font-size: 40px;line-height: 40px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\n" +
                "                                <span>&#160;</span>\n" +
                "                            </td>\n" +
                "                        </tr>\n" +
                "                    </tbody>\n" +
                "                </table>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "    </tbody>\n" +
                "</table>\n" +
                "                  \n" +
                "              <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\n" +
                "              </div>\n" +
                "            </div>\n" +
                "          <!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n" +
                "        </div>\n" +
                "      </div>\n" +
                "    </div>\n" +
                "    <div style=\"background-color:transparent;\">\n" +
                "      <div style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #FFFFFF;\" class=\"block-grid \">\n" +
                "        <div style=\"border-collapse: collapse;display: table;width: 100%;background-color:#FFFFFF;\">\n" +
                "          <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"background-color:transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width: 600px;\"><tr class=\"layout-full-width\" style=\"background-color:#FFFFFF;\"><![endif]-->\n" +
                "\n" +
                "              <!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\" width:600px; padding-right: 0px; padding-left: 20px; padding-top:5px; padding-bottom:5px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><![endif]-->\n" +
                "            <div class=\"col num12\" style=\"min-width: 320px;max-width: 600px;display: table-cell;vertical-align: top;\">\n" +
                "              <div style=\"background-color: transparent; width: 100% !important;\">\n" +
                "              <!--[if (!mso)&(!IE)]><!--><div style=\"border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 20px;\"><!--<![endif]-->\n" +
                "\n" +
                "                  \n" +
                "                    <div align=\"left\" class=\"img-container left fixedwidth \" style=\"padding-right: 5px;  padding-left: 5px;\">\n" +
                "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr style=\"line-height:0px;line-height:0px;\"><td style=\"padding-right: 5px; padding-left: 5px;\" align=\"left\"><![endif]-->\n" +
                "<div style=\"line-height:5px;font-size:1px\">&#160;</div>  <img class=\"left fixedwidth\" align=\"left\" border=\"0\" src=\"https://ekazipper.com/wp-content/uploads/2019/02/Logos-Header-4-unidades.png\" alt=\"Image\" title=\"Image\" style=\"outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: block !important;border: 0;height: auto;float: none;width: 100%;max-width: 493px\" width=\"493\">\n" +
                "<div style=\"line-height:25px;font-size:1px\">&#160;</div><!--[if mso]></td></tr></table><![endif]-->\n" +
                "</div>\n" +
                "\n" +
                "                  \n" +
                "              <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\n" +
                "              </div>\n" +
                "            </div>\n" +
                "          <!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n" +
                "        </div>\n" +
                "      </div>\n" +
                "    </div>\n" +
                "    <div style=\"background-color:transparent;\">\n" +
                "      <div style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #FFFFFF;\" class=\"block-grid \">\n" +
                "        <div style=\"border-collapse: collapse;display: table;width: 100%;background-color:#FFFFFF;\">\n" +
                "          <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"background-color:transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width: 600px;\"><tr class=\"layout-full-width\" style=\"background-color:#FFFFFF;\"><![endif]-->\n" +
                "\n" +
                "              <!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\" width:600px; padding-right: 0px; padding-left: 0px; padding-top:0px; padding-bottom:0px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><![endif]-->\n" +
                "            <div class=\"col num12\" style=\"min-width: 320px;max-width: 600px;display: table-cell;vertical-align: top;\">\n" +
                "              <div style=\"background-color: transparent; width: 100% !important;\">\n" +
                "              <!--[if (!mso)&(!IE)]><!--><div style=\"border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent; padding-top:0px; padding-bottom:0px; padding-right: 0px; padding-left: 0px;\"><!--<![endif]-->\n" +
                "\n" +
                "                  \n" +
                "                    <div class=\"\">\n" +
                "	<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 35px; padding-left: 35px; padding-top: 0px; padding-bottom: 0px;\"><![endif]-->\n" +
                "	<div style=\"color:#555555;font-family:'Open Sans', Helvetica, Arial, sans-serif;line-height:120%; padding-right: 35px; padding-left: 35px; padding-top: 0px; padding-bottom: 0px;\">	\n" +
                "		<div style=\"font-size:12px;line-height:14px;font-family:'Open Sans', Helvetica, Arial, sans-serif;color:#555555;text-align:left;\"><p style=\"margin: 0;font-size: 14px;line-height: 17px\"><span style=\"font-size: 24px; line-height: 28px; color: rgb(0, 0, 0);\"><strong>&#191Restablecer tu contrase&#241a?</strong></span></p></div>	\n" +
                "	</div>\n" +
                "	<!--[if mso]></td></tr></table><![endif]-->\n" +
                "</div>\n" +
                "                  \n" +
                "              <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\n" +
                "              </div>\n" +
                "            </div>\n" +
                "          <!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n" +
                "        </div>\n" +
                "      </div>\n" +
                "    </div>\n" +
                "    <div style=\"background-color:transparent;\">\n" +
                "      <div style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #FFFFFF;\" class=\"block-grid \">\n" +
                "        <div style=\"border-collapse: collapse;display: table;width: 100%;background-color:#FFFFFF;\">\n" +
                "          <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"background-color:transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width: 600px;\"><tr class=\"layout-full-width\" style=\"background-color:#FFFFFF;\"><![endif]-->\n" +
                "\n" +
                "              <!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\" width:600px; padding-right: 35px; padding-left: 35px; padding-top:35px; padding-bottom:40px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><![endif]-->\n" +
                "            <div class=\"col num12\" style=\"min-width: 320px;max-width: 600px;display: table-cell;vertical-align: top;\">\n" +
                "              <div style=\"background-color: transparent; width: 100% !important;\">\n" +
                "              <!--[if (!mso)&(!IE)]><!--><div style=\"border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent; padding-top:35px; padding-bottom:40px; padding-right: 35px; padding-left: 35px;\"><!--<![endif]-->\n" +
                "\n" +
                "                  \n" +
                "                    <div class=\"\">\n" +
                "	<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 5px; padding-top: 0px; padding-bottom: 20px;\"><![endif]-->\n" +
                "	<div style=\"color:#555555;font-family:'Open Sans', Helvetica, Arial, sans-serif;line-height:150%; padding-right: 0px; padding-left: 5px; padding-top: 0px; padding-bottom: 20px;\">	\n" +
                "		<div style=\"font-size:12px;line-height:18px;font-family:'Open Sans', Helvetica, Arial, sans-serif;color:#555555;text-align:left;\"><p style=\"margin: 0;font-size: 14px;line-height: 21px\">Hola "+usuario.getNombre()+", Si solicitaste un restablecimiento de contrase&#241a, haz clic en el bot&#243n que aparece a continuaci&#243n. Si no solicitaste esto, ignora este correo electr&#243nico.</p></div>	\n" +
                "	</div>\n" +
                "	<!--[if mso]></td></tr></table><![endif]-->\n" +
                "</div>\n" +
                "                  \n" +
                "                  \n" +
                "                    \n" +
                "<div align=\"left\" class=\"button-container left \" style=\"padding-right: 0px; padding-left: 0px; padding-top:0px; padding-bottom:0px;\">\n" +
                "  <!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-spacing: 0; border-collapse: collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top:0px; padding-bottom:0px;\" align=\"left\"><v:roundrect xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:w=\"urn:schemas-microsoft-com:office:word\" href=\"link\" style=\"height:31pt; v-text-anchor:middle; width:172pt;\" arcsize=\"3%\" strokecolor=\"#0068A5\" fillcolor=\"#0068A5\"><w:anchorlock/><v:textbox inset=\"0,0,0,0\"><center style=\"color:#FFFFFF; font-family:'Open Sans', Helvetica, Arial, sans-serif; font-size:16px;\"><![endif]-->\n" +
                "    <a href=\"http://localhost:4200/procesar-token/"+usuario.getToken()+"\" target=\"_blank\" style=\"display: block;text-decoration: none;-webkit-text-size-adjust: none;text-align: center;color: #FFFFFF; background-color: #0068A5; border-radius: 1px; -webkit-border-radius: 1px; -moz-border-radius: 1px; max-width: 230px; width: 190px;width: auto; border-top: 0px solid transparent; border-right: 0px solid transparent; border-bottom: 0px solid transparent; border-left: 0px solid transparent; padding-top: 5px; padding-right: 20px; padding-bottom: 5px; padding-left: 20px; font-family: 'Open Sans', Helvetica, Arial, sans-serif;mso-border-alt: none\">\n" +
                "      <span style=\"font-family:'Open Sans', Helvetica, Arial, sans-serif;font-size:16px;line-height:32px;\"><span style=\"font-size: 15px; line-height: 30px;\"><strong>Restablecer contrase&#241a</strong></span></span>\n" +
                "    </a>\n" +
                "  <!--[if mso]></center></v:textbox></v:roundrect></td></tr></table><![endif]-->\n" +
                "</div>\n" +
                "\n" +
                "                  \n" +
                "              <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\n" +
                "              </div>\n" +
                "            </div>\n" +
                "          <!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n" +
                "        </div>\n" +
                "      </div>\n" +
                "    </div>\n" +
                "    <div style=\"background-color:transparent;\">\n" +
                "      <div style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #0068A5;\" class=\"block-grid two-up \">\n" +
                "        <div style=\"border-collapse: collapse;display: table;width: 100%;background-color:#0068A5;\">\n" +
                "          <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"background-color:transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width: 600px;\"><tr class=\"layout-full-width\" style=\"background-color:#0068A5;\"><![endif]-->\n" +
                "\n" +
                "              <!--[if (mso)|(IE)]><td align=\"center\" width=\"300\" style=\" width:300px; padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><![endif]-->\n" +
                "            <div class=\"col num6\" style=\"max-width: 320px;min-width: 300px;display: table-cell;vertical-align: top;\">\n" +
                "              <div style=\"background-color: transparent; width: 100% !important;\">\n" +
                "              <!--[if (!mso)&(!IE)]><!--><div style=\"border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;\"><!--<![endif]-->\n" +
                "\n" +
                "                  \n" +
                "                    <div class=\"\">\n" +
                "	<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 10px; padding-left: 35px; padding-top: 25px; padding-bottom: 30px;\"><![endif]-->\n" +
                "	<div style=\"color:#555555;font-family:'Open Sans', Helvetica, Arial, sans-serif;line-height:120%; padding-right: 10px; padding-left: 35px; padding-top: 25px; padding-bottom: 30px;\">	\n" +
                "		<div style=\"font-size:12px;line-height:14px;font-family:'Open Sans', Helvetica, Arial, sans-serif;color:#555555;text-align:left;\"><p style=\"margin: 0;font-size: 12px;line-height: 14px\"><span style=\"font-size: 17px; line-height: 20px; color: rgb(255, 255, 255);\">EKA CORPORACI&#211N S.A.</span></p><p style=\"margin: 0;font-size: 12px;line-height: 14px\"><span style=\"font-size: 17px; line-height: 20px; color: rgb(255, 255, 255);\">Calle 46 A No 1-61</span></p><p style=\"margin: 0;font-size: 12px;line-height: 14px\"><span style=\"font-size: 17px; line-height: 20px; color: rgb(255, 255, 255);\">(+57) 2 486 2222</span></p></div>	\n" +
                "	</div>\n" +
                "	<!--[if mso]></td></tr></table><![endif]-->\n" +
                "</div>\n" +
                "</body></html>";
        emailController.sendMail(usuario.getEmail(), asunto, cuerpo);
    }





}


