package com.hitpixel.paymentgwinvoice.services;

import com.hitpixel.paymentgwinvoice.models.Invoice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Service
@Slf4j
public class EmailService {

    @Autowired(required = false)
    private JavaMailSender javaMailSender;

    @Async
    public void send(String clientName, String to, Invoice invoice) {
        try {
            if(javaMailSender == null){
                log.info("email not enabled. Please enable it on properties file");
                return;
            }
            MimeMessage msg = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(msg, false);
            helper.setTo(to);
            Date date = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.MONTH, -1);
            helper.setSubject("Invoice from HitPixel from " + new SimpleDateFormat("MMM").format(c.getTime()) + " " + new SimpleDateFormat("yyyy").format(c.getTime()));

            helper.setText(createTemplate(clientName, invoice));
            javaMailSender.send(msg);
        }catch (MessagingException msgExp){
            log.error("error while sending email, error is: "+ msgExp.getLocalizedMessage());
        }
    }

    public String createTemplate(String client, Invoice invoice){
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DAY_OF_MONTH, 10);
        String dueDate = "";
        String html = "<style>\n" +
                "\t\t\tbody {\n" +
                "\t\t\t\tfont-family: 'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif;\n" +
                "\t\t\t\ttext-align: center;\n" +
                "\t\t\t\tcolor: #777;\n" +
                "\t\t\t}\n" +
                "\n" +
                "\t\t\tbody h1 {\n" +
                "\t\t\t\tfont-weight: 300;\n" +
                "\t\t\t\tmargin-bottom: 0px;\n" +
                "\t\t\t\tpadding-bottom: 0px;\n" +
                "\t\t\t\tcolor: #000;\n" +
                "\t\t\t}\n" +
                "\n" +
                "\t\t\tbody h3 {\n" +
                "\t\t\t\tfont-weight: 300;\n" +
                "\t\t\t\tmargin-top: 10px;\n" +
                "\t\t\t\tmargin-bottom: 20px;\n" +
                "\t\t\t\tfont-style: italic;\n" +
                "\t\t\t\tcolor: #555;\n" +
                "\t\t\t}\n" +
                "\n" +
                "\t\t\tbody a {\n" +
                "\t\t\t\tcolor: #06f;\n" +
                "\t\t\t}\n" +
                "\n" +
                "\t\t\t.invoice-box {\n" +
                "\t\t\t\tmax-width: 800px;\n" +
                "\t\t\t\tmargin: auto;\n" +
                "\t\t\t\tpadding: 30px;\n" +
                "\t\t\t\tborder: 1px solid #eee;\n" +
                "\t\t\t\tbox-shadow: 0 0 10px rgba(0, 0, 0, 0.15);\n" +
                "\t\t\t\tfont-size: 16px;\n" +
                "\t\t\t\tline-height: 24px;\n" +
                "\t\t\t\tfont-family: 'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif;\n" +
                "\t\t\t\tcolor: #555;\n" +
                "\t\t\t}\n" +
                "\n" +
                "\t\t\t.invoice-box table {\n" +
                "\t\t\t\twidth: 100%;\n" +
                "\t\t\t\tline-height: inherit;\n" +
                "\t\t\t\ttext-align: left;\n" +
                "\t\t\t\tborder-collapse: collapse;\n" +
                "\t\t\t}\n" +
                "\n" +
                "\t\t\t.invoice-box table td {\n" +
                "\t\t\t\tpadding: 5px;\n" +
                "\t\t\t\tvertical-align: top;\n" +
                "\t\t\t}\n" +
                "\n" +
                "\t\t\t.invoice-box table tr td:nth-child(2) {\n" +
                "\t\t\t\ttext-align: right;\n" +
                "\t\t\t}\n" +
                "\n" +
                "\t\t\t.invoice-box table tr.top table td {\n" +
                "\t\t\t\tpadding-bottom: 20px;\n" +
                "\t\t\t}\n" +
                "\n" +
                "\t\t\t.invoice-box table tr.top table td.title {\n" +
                "\t\t\t\tfont-size: 45px;\n" +
                "\t\t\t\tline-height: 45px;\n" +
                "\t\t\t\tcolor: #333;\n" +
                "\t\t\t}\n" +
                "\n" +
                "\t\t\t.invoice-box table tr.information table td {\n" +
                "\t\t\t\tpadding-bottom: 40px;\n" +
                "\t\t\t}\n" +
                "\n" +
                "\t\t\t.invoice-box table tr.heading td {\n" +
                "\t\t\t\tbackground: #eee;\n" +
                "\t\t\t\tborder-bottom: 1px solid #ddd;\n" +
                "\t\t\t\tfont-weight: bold;\n" +
                "\t\t\t}\n" +
                "\n" +
                "\t\t\t.invoice-box table tr.details td {\n" +
                "\t\t\t\tpadding-bottom: 20px;\n" +
                "\t\t\t}\n" +
                "\n" +
                "\t\t\t.invoice-box table tr.item td {\n" +
                "\t\t\t\tborder-bottom: 1px solid #eee;\n" +
                "\t\t\t}\n" +
                "\n" +
                "\t\t\t.invoice-box table tr.item.last td {\n" +
                "\t\t\t\tborder-bottom: none;\n" +
                "\t\t\t}\n" +
                "\n" +
                "\t\t\t.invoice-box table tr.total td:nth-child(2) {\n" +
                "\t\t\t\tborder-top: 2px solid #eee;\n" +
                "\t\t\t\tfont-weight: bold;\n" +
                "\t\t\t}\n" +
                "\n" +
                "\t\t\t@media only screen and (max-width: 600px) {\n" +
                "\t\t\t\t.invoice-box table tr.top table td {\n" +
                "\t\t\t\t\twidth: 100%;\n" +
                "\t\t\t\t\tdisplay: block;\n" +
                "\t\t\t\t\ttext-align: center;\n" +
                "\t\t\t\t}\n" +
                "\n" +
                "\t\t\t\t.invoice-box table tr.information table td {\n" +
                "\t\t\t\t\twidth: 100%;\n" +
                "\t\t\t\t\tdisplay: block;\n" +
                "\t\t\t\t\ttext-align: center;\n" +
                "\t\t\t\t}\n" +
                "\t\t\t}\n" +
                "\t\t</style>\n" +
                "\t</head>\n" +
                "\n" +
                "\t<body>\n" +
                "\t\t<div class=\"invoice-box\">\n" +
                "\t\t\t<table>\n" +
                "\t\t\t\t<tr class=\"top\">\n" +
                "\t\t\t\t\t<td colspan=\"2\">\n" +
                "\t\t\t\t\t\t<table>\n" +
                "\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t<td class=\"title\">\n" +
                "\t\t\t\t\t\t\t\t\t<img src=\"./images/logo.png\" alt=\"Company logo\" style=\"width: 100%; max-width: 300px\" />\n" +
                "\t\t\t\t\t\t\t\t</td>\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t<td>\n" +
                "\t\t\t\t\t\t\t\t\tInvoice #: "+invoice.getId()+"<br />\n" +
                "\t\t\t\t\t\t\t\t\tCreated: "+sdf.format(new Date())+"<br />\n" +
                "\t\t\t\t\t\t\t\t\tDue: "+sdf.format(c.getTime())+"\n" +
                "\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t</td>\n" +
                "\t\t\t\t</tr>\n" +
                "\n" +
                "\t\t\t\t<tr class=\"information\">\n" +
                "\t\t\t\t\t<td colspan=\"2\">\n" +
                "\t\t\t\t\t\t<table>\n" +
                "\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t<td>\n" +
                "\t\t\t\t\t\t\t\t\tHitPixel LLC.<br />\n" +
                "\t\t\t\t\t\t\t\t\t12345 Opus tower<br />\n" +
                "\t\t\t\t\t\t\t\t\tDubai, UAE 12345\n" +
                "\t\t\t\t\t\t\t\t</td>\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t<td>\n" +
                "\t\t\t\t\t\t\t\t\t"+client+"<br />\n" +
                "\t\t\t\t\t\t\t\t\t Maran <br />\n" +
                "\t\t\t\t\t\t\t\t\tmaran@example.com\n" +
                "\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t</td>\n" +
                "\t\t\t\t</tr>\n" +
                "\n" +
                "\t\t\t\t<tr class=\"heading\">\n" +
                "\t\t\t\t\t<td>Payment Method</td>\n" +
                "\n" +
                "\t\t\t\t\t<td>Check #</td>\n" +
                "\t\t\t\t</tr>\n" +
                "\n" +
                "\t\t\t\t<tr class=\"details\">\n" +
                "\t\t\t\t\t<td>Check</td>\n" +
                "\n" +
                "\t\t\t\t\t<td>1000</td>\n" +
                "\t\t\t\t</tr>\n" +
                "\n" +
                "\t\t\t\t<tr class=\"heading\">\n" +
                "\t\t\t\t\t<td>Transactions</td>\n" +
                "\n" +
                "\t\t\t\t<tr class=\"heading\">\n" +
                "\t\t\t\t\t<td>No of Transactions</td>\n" +
                "\n" +
                "\t\t\t\t\t<td>Price</td>\n" +
                "\t\t\t\t</tr>\n" +
                "\n" +
                "\t\t\t\t<tr class=\"item\">\n" +
                "\t\t\t\t\t<td>Approved Transactions</td>\n" +
                "\n" +
                "\t\t\t\t<tr class=\"item\">\n" +
                "\t\t\t\t\t<td>"+invoice.getApprovedTransactions()+"s</td>\n" +
                "\n" +
                "\t\t\t\t\t<td>"+invoice.getApprovedAmount()+"</td>\n" +
                "\t\t\t\t</tr>\n" +
                "\n" +
                "\t\t\t\t<tr class=\"item\">\n" +
                "\t\t\t\t\t<td>Refunded Transactions</td>\n" +
                "\n" +
                "\t\t\t\t<tr class=\"item\">\n" +
                "\t\t\t\t\t<td>"+invoice.getRefundedTransactions()+"</td>\n" +
                "\n" +
                "\t\t\t\t\t<td>"+invoice.getRefundedAmount()+"</td>\n" +
                "\t\t\t\t</tr>\n" +
                "\n" +
                "\t\t\t\t<tr class=\"total\">\n" +
                "\t\t\t\t\t<td></td>\n" +
                "\n" +
                "\t\t\t\t\t<td>Total: "+invoice.getTotalAmount()+"</td>\n" +
                "\t\t\t\t</tr>\n" +
                "\t\t\t</table>\n" +
                "\t\t</div>\n" +
                "\t</body>";
        return html;
    }
}
