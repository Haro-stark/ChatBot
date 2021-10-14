package com.example.ChatBot.Util;

import com.twilio.Twilio;
import com.twilio.exception.AuthenticationException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SmsUtil {

    private static final String ACCOUNT_SID = "AC81d02d144295cdbec7e24bd18e361909";
    private static final String AUTH_TOKEN = "bc88f2d65d3186c714d130e8d3dde448";
    private static final String fromNumber = "+19803006396";

    public static String sendSms(String toNumber, String message) {
        try {
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
            Message.creator(new PhoneNumber(toNumber), new PhoneNumber(fromNumber), message).create();
            return ("Successfully sent");
        }
        catch (AuthenticationException e) {
            return("Authentication error while sending message to the contact number! \n" + e.getMessage());
        }
        catch (Exception e) {
            return ("Unable to send sms\n" + e.getMessage());
        }

    }
}
