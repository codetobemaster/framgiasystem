package com.framgia.attendance.web.top;

import java.io.FileNotFoundException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.validation.validator.EmailAddressValidator;
import org.seasar.framework.container.annotation.tiger.Binding;

import com.framgia.attendance.dao.UserAccountDao;
import com.framgia.attendance.entity.UserAccount;
import com.framgia.attendance.logic.mail.ConfigMail;
import com.framgia.attendance.logic.mail.InfoEmailSingleton;
import com.framgia.attendance.logic.mail.MailLogic;
import com.framgia.attendance.logic.mail.MailTemplate;
import com.framgia.attendance.web.template.BaseWebPage;
import com.framgia.attendance.web.template.WebTemplatePage;

public class ForgetPasswordPage extends WebTemplatePage {

	private static final long serialVersionUID = 1L;
	private static final String BASE_FILE = "mail_reset_password_%s.tmpl";
	private static final String SUBJECT = "Uzabase Reset Password";
	private String email;
	private FeedbackPanel feedback;
	private MailTemplate mailTemplate;
	private UserAccount userAccount;
	private String hashKey;
	private String content;

	private MailLogic mailLogic;
	@Binding
	UserAccountDao userAccountDao;
	@Binding
	StringResourceModel forgotPasswordSuccess=new StringResourceModel("forgetPasswordPage.sentMailMessage", this, null);
	StringResourceModel emailDoesnotExist=new StringResourceModel("forgetPasswordPage.emailDoesntExist", this, null);
	

	public ForgetPasswordPage(PageParameters parameters) {
	    try {
            mailLogic=new MailLogic((ConfigMail)InfoEmailSingleton.getInstance());
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		mailTemplate = new MailResetPassTemplate();
		add(new Image("logo", BaseWebPage.UB_LOGO));
		add(feedback = new FeedbackPanel("feedback"));
		feedback.setOutputMarkupId(true);
		Form<?> form = new Form<Void>("form");
		form.add(new EmailTextField("email", new PropertyModel(this, "email"),
				EmailAddressValidator.getInstance()));
		form.add(new AjaxButton("send", form) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				userAccount = userAccountDao.findByEmail(email);
//				resetPassword = resetPasswordDao.findByEmailAddress(email);
				hashKey = generateUrl();
				if (userAccount != null) {
					String language = (userAccount.getDefaultLanguage() == 1) ? "en"
							: "jp";
					content = mailTemplate.getMail(BASE_FILE, language);

					// Send email
					new Thread(new SendMailThread()).start();
					info(forgotPasswordSuccess.getString());
					this.setEnabled(false);
				} else {
					error(emailDoesnotExist.getString());
				}
				target.add(this);
				target.add(feedback);
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				super.onError(target, form);
				target.add(feedback);
			}
		});
		add(form);
	}

	private String generateUrl() {
		String now = new Date().toString();
		String hashKey = now + email;
		String encryptedHash = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(hashKey.getBytes());
			byte[] bytes = md.digest();
			// This bytes[] has bytes in decimal format;
			// Convert it to hexadecimal format
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16)
						.substring(1));
			}
			encryptedHash = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return encryptedHash;
	}

	private String getServerHost() {
		return RequestCycle.get().getUrlRenderer()
				.renderFullUrl(Url.parse("reset"));
	}

	class MailResetPassTemplate extends MailTemplate {

		private static final long serialVersionUID = 1L;

		@Override
		protected Map<String, Object> setVariables() {
			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put("name", userAccount.getUsername());
			variables.put("resetPasswordURL", getServerHost() + "?&hash_key="
					+ hashKey);
			return variables;
		}
	}

	class SendMailThread implements Runnable {

		@Override
		public void run() {
			boolean sent = mailLogic.sendMail(email, null, email, SUBJECT, content);
//			if (sent) {
//				// update DB
//				if (resetPassword != null) {
//					resetPassword.setHashKey(hashKey);
//					resetPassword.setStatus(false);
//					resetPassword.setResetTime(new Date());
//					resetPasswordDao.update(resetPassword);
//				} else {
//					resetPassword = new ResetPassword();
//					resetPassword.setEmailAddress(email);
//					resetPassword.setHashKey(hashKey);
//					resetPassword.setStatus(false);
//					resetPassword.setResetTime(new Date());
//					resetPasswordDao.insert(resetPassword);
//				}
//			}

		}
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		response.render(CssHeaderItem.forReference(BaseWebPage.BOOTSTRAP_CSS));
		response.render(CssHeaderItem.forReference(BaseWebPage.ATTENDANCE_CSS));
		response.render(JavaScriptHeaderItem
				.forReference(BaseWebPage.BOOTSTRAP_JS));

	}
}
