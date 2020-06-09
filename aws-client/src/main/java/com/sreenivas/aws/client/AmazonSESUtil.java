/**
 * 
 */
package com.sreenivas.aws.client;

import java.io.IOException;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest; 

/**
 * @author sreenivask
 *
 */
public class AmazonSESUtil {

	// Replace sender@example.com with your "From" address.
	// This address must be verified with Amazon SES.
	static final String FROM = "sender@example.com";

	// Replace recipient@example.com with a "To" address. If your account
	// is still in the sandbox, this address must be verified.
	static final String TO = "recipient@example.com";

	// The subject line for the email.
	static final String SUBJECT = "Amazon SES test (AWS SDK for Java)";

	// The HTML body for the email.
	static final String HTMLBODY = "<h1>Amazon SES test (AWS SDK for Java)</h1>";

	// The email body for recipients with non-HTML email clients.
	static final String TEXTBODY = "This email was sent through Amazon SES using the AWS SDK for Java.";

	// Replace US_WEST_2 with the AWS Region you're using for Amazon SES.
	static final Regions region = Regions.US_WEST_2;

	private static final String ACCESS_KEY_ID = "abcd123";
	private static final String SECRET_ACCESS_KEY = "xyz123";
	
	static AmazonSimpleEmailServiceClient client = null;
	
	
	public static void initialize() {
		try {

			//AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder.standard() .withRegion(region).build();

			BasicAWSCredentials credentials = new BasicAWSCredentials(ACCESS_KEY_ID, SECRET_ACCESS_KEY);
			client =  new AmazonSimpleEmailServiceClient(credentials);
			
		} catch (Exception ex) {
			throw new AmazonClientException("Please make sure that your AWS credentials are correct.", ex);
		}
		
	}
	
	public static void sendEmail() {
		try {
			
			SendEmailRequest request = new SendEmailRequest()
					.withDestination(
							new Destination().withToAddresses(TO))
					.withMessage(new Message()
							.withBody(new Body()
									.withHtml(new Content()
											.withCharset("UTF-8").withData(HTMLBODY))
									.withText(new Content()
											.withCharset("UTF-8").withData(TEXTBODY)))
							.withSubject(new Content()
									.withCharset("UTF-8").withData(SUBJECT)))
					.withSource(FROM);
			client.sendEmail(request);
			System.out.println("Email sent!");
			
		} catch (Exception ex) {
			System.out.println("The email was not sent. Error message: " + ex.getMessage());
		}
	}

	public static void main(String[] args) throws IOException {

		initialize();
		sendEmail();
		
	}
}
