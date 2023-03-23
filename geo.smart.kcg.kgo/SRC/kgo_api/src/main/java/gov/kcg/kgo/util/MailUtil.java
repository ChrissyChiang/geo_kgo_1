package gov.kcg.kgo.util;

import freemarker.template.Configuration;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.annotation.Resource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 寄信工具.
 */
@Component
public class MailUtil {
    /**
     * logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MailUtil.class);

    @Resource
    private Environment environment;

    @Autowired
    private Configuration freemarkerConfig;


    @Value("${mail.fromMail.addr}")
    private String FROM;
    @Value("${geo.mail.host}")
    private String HOST;
    @Value("${geo.mail.port}")
    private Integer PORT;
    @Value("${geo.mail.protocol}")
    private String PROTOCOL;
    @Value("${geo.mail.ssl.protocol}")
    private String SSL_PROTOCOL;
    @Value("${geo.mail.username}")
    private String USER_NAME;
    @Value("${geo.mail.password}")
    private String PASSWRD;
    @Value("${geo.mail.starttls}")
    private boolean STARTTLES;
    @Value("${geo.mail.starttls}")
    private boolean SSL;
    @Value("${geo.mail.smtp.auth}")
    private boolean AUTH;

    /**
     * 是否寄信(正式機、公司內部測試機 才寄)
     */
    @Value("${mail.sendmail}")
    private String sendmail;

    /**
     * Session 預設Mail環境設定檔
     * @return Session
     */
    private Session mailSessionSetup() {
        Properties props = new Properties();
        props.put("mail.smtp.host", HOST);
        props.put("mail.smtp.port", PORT);
        props.put("mail.smtp.auth", AUTH);
        props.put("mail.smtp.starttls.enable", STARTTLES);
        props.put("mail.smtp.starttls.required",STARTTLES);
        props.put("mail.smtp.ssl.enable", SSL);
        props.put("mail.smtp.ssl.protocols", SSL_PROTOCOL);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.debug", "TRUE");
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USER_NAME, PASSWRD);
            }
        });
        return session;
    }

    /**
     * 送出信件
     */
    public void transportSend(Session session, Message message) throws Exception{
        Transport transport = session.getTransport(PROTOCOL);
        transport.connect(HOST, PORT,
                USER_NAME, PASSWRD);
        //LogUtil.debug("MailService sendToSmtpMail begin...");
        Transport.send(message);
    }


    /**
     * 寄信作業(套版).
     *
     * @param sendEmailInfo the send email info
     * @param templateName  the template name
     * @throws Exception
     */
    @Async
    public void sendTemplateMail(String[] to, String[] cc, String subject, Map<String, Object> model,
                                 String templatePath) throws Exception {
        try {
            String templateString = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerConfig.getTemplate(templatePath), model);
            // 發送Email
            sendMail(to, cc, subject, templateString);
            LOGGER.info(">>>>> Send mail success : " + JsonUtil.toJSONString(to));
        } catch (Exception e) {
            LOGGER.error(">>>>> Send mail fail : " + JsonUtil.toJSONString(to));
            throw e;
        }
    }

    /**
     * 寄信作業 不須CC給其他人 (套版).
     */
    @Async
    public void sendTemplateMailNoCC(String[] to, String subject, Map<String, Object> model, String templatePath)
            throws Exception {
        try {
            String templateString = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerConfig.getTemplate(templatePath), model);
            // 發送Email
            sendMail(to, null, subject, templateString);
            LOGGER.info(">>>>> Send mail success : " + JsonUtil.toJSONString(to));

        } catch (Exception e) {
            LOGGER.error(">>>>> Send mail fail : " + JsonUtil.toJSONString(to));
            throw e;
        }
    }

    /**
     * 發送Email
     *
     * @param to
     * @param cc
     * @param subject
     * @param content
     * @throws MessagingException
     */
    @Async
    public void sendMail(String[] to, String[] cc, String subject, String content) throws Exception {
        try {
            boolean isSendMail = Boolean.valueOf(StringUtils.defaultIfBlank(sendmail, "false"));
            // modify 20210120 是否寄信(正式機、公司內部測試機 才寄)
            if (isSendMail) {
                Session session = mailSessionSetup();
                MimeMessage message = new MimeMessage(session);
                MimeMessageHelper helper = new MimeMessageHelper(message, true,"UTF-8");
                helper.setFrom(FROM);
                helper.setTo(to);

                if (ArrayUtils.isNotEmpty(cc)) {
                    helper.setCc(cc);
                }
                helper.setSubject(subject);
                helper.setText(content, true);
//                send(session, message);
                LOGGER.info("test email start ... ");
                transportSend(session,message);

                LOGGER.info(">>>>> Send mail success : " + JsonUtil.toJSONString(to));
            } else {
                LOGGER.info(">>>>> 預設不寄信 isSendMail  : " + isSendMail + " to: " + JsonUtil.toJSONString(to));
            }
        } catch (Exception e) {
            LOGGER.error(">>>>> Send mail fail : " + JsonUtil.toJSONString(to));
            throw e;
        }
    }



    private void send(Session session, MimeMessage message) throws Exception {
        try {
            String username = SpringUtil.getProperty("geo.mail.username");
            String password = SpringUtil.getProperty("geo.mail.password");
//            message.setFrom("kgoservice@kcg.gov.tw"); //這裏面的郵件地址會被換成主機送信者的mail
//            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to[0]));
            Transport transport = session.getTransport("smtp");
            transport.connect(SpringUtil.getProperty("geo.mail.host"), Integer.parseInt(SpringUtil.getProperty("geo.mail.port")),
                    username, password);
            //LogUtil.debug("MailService sendToSmtpMail begin...");
            Transport.send(message);
            LOGGER.info("jgallop email ... finish....");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info("jgallop email ... fail....");
        }
    }


    /**
     * 寄信作業(套版).
     *
     * @param sendEmailInfo the send email info
     * @param templateName  the template name
     * @throws MessagingException
     */
//	@Async
//	public void sendTemplateMail(String[] to, String[] cc, String subject, String content, String templatePath) throws MessagingException {
//		try {

//			String[] setTo = CollectionUtils.isNotEmpty(setToList) ? setToList.stream().toArray(String[]::new)
//					: new String[0];
//			String[] setCc = CollectionUtils.isNotEmpty(setCcList) ? setCcList.stream().toArray(String[]::new)
//					: new String[0];
//			String subJect = sendEmailInfo.getSetSubject();
//			Context context = sendEmailInfo.getContext();
    // 套版
//			String emailContent = templateEngine.process(templatePath, content);
//			// 發送Email
//			sendMail(setTo, setCc, subJect, emailContent);
//
//			LOGGER.info(">>>>> Send mail success : " + JsonUtil.toJSONString(sendEmailInfo));
//
//		} catch (Exception e) {
//			LOGGER.error(">>>>> Send mail fail : " + JsonUtil.toJSONString(sendEmailInfo));
//			throw e;
//		}
//	}
    public void sendTemplateMailwithImg(String[] to, String[] cc, String subject, Map<String, Object> model,
                                        String templatePath, List<String> filesPath, boolean deleteFile ) throws Exception {
        try {
            String templateString = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerConfig.getTemplate(templatePath), model);
            List<File> files = new ArrayList();
            if(filesPath.size()>0){
                for(String path : filesPath){
                    files.add(new File(path));
                }
            }
            sendMailwithImg(to, cc, subject, templateString, files);
            LOGGER.info(">>>>> Send mail success : " + JsonUtil.toJSONString(to));
            if(deleteFile){
                LOGGER.info(">>>>> Delete email attached file start!! ");
                files.forEach(File::delete);
            }

        } catch (Exception e) {
            LOGGER.error(">>>>> Send mail fail : " + JsonUtil.toJSONString(to));
            System.out.println(e);
            throw e;
        }
    }

    public void sendMailwithImg(String[] to, String[] cc, String subject, String content, List<File> files) throws Exception {
            boolean isSendMail = Boolean.valueOf(StringUtils.defaultIfBlank(sendmail, "false"));
            // modify 20210120 是否寄信(正式機、公司內部測試機 才寄)
            if (isSendMail) {
                Session session = this.mailSessionSetup();
                MimeMessage message = new MimeMessage(session);
                message.setFrom(FROM);
                for(String address : ArrayUtils.nullToEmpty(to)){
                    if(StringUtils.isNotBlank(address)) {
                        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(address));
                    }
                }
                for(String address : ArrayUtils.nullToEmpty(cc)){
                    if(StringUtils.isNotBlank(address)) {
                        message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(address));
                    }
                }
                message.setSubject(subject);

                Multipart multipart = new MimeMultipart("related");
                //信箱套版內容
                MimeBodyPart messageBodyPart = new MimeBodyPart();
                messageBodyPart.setContent(content, "text/html; charset=UTF-8");
                multipart.addBodyPart(messageBodyPart);

                //插入圖片
                if (files.size() != 0) {
                    AtomicInteger autoInt = new AtomicInteger(1);
                    for(File file : files ){
                        MimeBodyPart mimeImgBodyPart = new MimeBodyPart();
                        mimeImgBodyPart.setDisposition("inline");
                        mimeImgBodyPart.setContentID("<file"+autoInt.getAndAdd(1)+">");
                        FileDataSource dataSource = new FileDataSource(file);
                        mimeImgBodyPart.setDataHandler(new DataHandler(dataSource));
                        multipart.addBodyPart(mimeImgBodyPart);
                    }
                }

                message.setContent(multipart);
//                send(session, message);
                this.transportSend(session,message);
                LOGGER.info(">>>>> Send mail success : " + JsonUtil.toJSONString(to));

            } else {
                LOGGER.info(">>>>> 預設不寄信 isSendMail  : " + isSendMail + " to: " + JsonUtil.toJSONString(to));
            }
    }


}
