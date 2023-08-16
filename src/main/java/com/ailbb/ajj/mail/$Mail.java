package com.ailbb.ajj.mail;

import com.ailbb.ajj.$;
import com.ailbb.ajj.entity.$Result;
import org.apache.commons.lang.StringUtils;

import jakarta.mail.*;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/*
 * 发送邮件
 * Created by WildMrZhang on 2017/3/6.
 */
public class $Mail {
    private boolean auth = true; // 发送服务器需要身份验证
    private boolean debug = false; // 开启debug调试
    private String host = "14.17.57.217"; // 设置邮件服务器主机名
    private String protocol = "smtp"; // 发送邮件协议名称
    private String senderEmail; // 发件人邮箱
    private String username; // 邮件用户名
    private String password; // 邮件密码
    private String[] recipientTOEmails = new String[]{}; // 接收邮件人邮箱
    private String[] recipientCCEmails = new String[]{}; // 接收邮件人邮箱
    private String[] recipientBCCEmails = new String[]{}; // 接收邮件人邮箱

    /*
     * 发送邮件
     * @param title 标题
     * @param text 内容
     * @return $Result 结构体
     */
    public $Result send(String title, String text, List<String> recipientTOEmails)  {
        return send(title, text, recipientTOEmails.toArray(new String[recipientTOEmails.size()]));
    }

    /*
     * 发送邮件
     * @param title 标题
     * @param text 内容
     * @param recipientTOEmails 接受邮件地址
     * @return $Result 结构体
     */
    public $Result send(String title, String text, String... recipientTOEmails)  {
        $Result rs = $.result();

        try {

            // 设置环境信息
            Session session = Session.getInstance(getMailProperties(), new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });
            // 创建邮件对象
            Message msg = new MimeMessage(session);

            msg.setSubject(title);
            // 设置邮件内容
            msg.setText(text);

            // 设置发件人
            msg.setFrom(new InternetAddress(senderEmail));

            boolean isRecipientEmpty = $.isEmptyOrNull(recipientTOEmails);
            String _recipientTOEmails = $.string.join((isRecipientEmpty ? this.recipientTOEmails : recipientTOEmails), ",");
            String _recipientCCEmails = $.string.join((isRecipientEmpty ? recipientCCEmails : null), ",");
            String _recipientBCCEmails = $.string.join((isRecipientEmpty ? recipientBCCEmails : null), ",");

            if($.isEmptyOrNull(_recipientTOEmails)) return rs;
            // 设置发送人
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(_recipientTOEmails));

            // 抄送人
            if(!$.isEmptyOrNull(_recipientCCEmails)) msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(_recipientCCEmails));

            // 暗送人
            if(!$.isEmptyOrNull(_recipientBCCEmails)) msg.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(_recipientBCCEmails));

            // 记录日志
            rs.addMessage($.info(
                    String.format("向 [ %s ] 发送邮件，抄送 [%s]，暗送 [%s], \r\n内容 [ %s ]",
                            _recipientTOEmails,
                            _recipientCCEmails,
                            _recipientBCCEmails,
                            msg)
            ));
            Transport.send(msg);

        } catch (AddressException e) {
            rs.addError($.exception(e));
        } catch (MessagingException e) {
            rs.addError($.exception(e));
        }

        return rs;
    }

    /*
     * 封装环境变量信息
     * @return Properties 文件对象
     */
    private Properties getMailProperties(){
        Properties props = new Properties(); // 环境变量信息

        props.setProperty("mail.debug", String.valueOf(isDebug()));
        props.setProperty("mail.smtp.auth", String.valueOf(isAuth()));
        props.setProperty("mail.host", getHost());
        props.setProperty("mail.transport.protocol", getProtocol());

        return props;
    }

    public boolean isDebug() {
        return debug;
    }

    public $Mail setDebug(boolean debug) {
        this.debug = debug;
        return this;
    }

    public boolean isAuth() {
        return auth;
    }

    public $Mail setAuth(boolean auth) {
        this.auth = auth;
        return this;
    }

    public String getHost() {
        return host;
    }

    public $Mail setHost(String host) {
        this.host = host;
        return this;
    }

    public String getProtocol() {
        return protocol;
    }

    public $Mail setProtocol(String protocol) {
        this.protocol = protocol;
        return this;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public $Mail setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public $Mail setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public $Mail setPassword(String password) {
        this.password = password;
        return this;
    }

    public String[] getrecipientTOEmails() {
        return recipientTOEmails;
    }

    public $Mail setrecipientTOEmails(String[] recipientTOEmails) {
        this.recipientTOEmails = recipientTOEmails;
        return this;
    }
}
