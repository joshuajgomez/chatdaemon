package com.joshgm3z.chatdaemon.common.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ContactFetcherTest {

    @Test
    public void cropTo10Digits() {
        String phoneNumber1 = "+917012399284";
        String phoneNumber2 = "+8190-7176-2281";
        String phoneNumber3 = "974-236-5102";
        String phoneNumber4 = "9847144525";
        String phoneNumber5 = "123";
        assertEquals(10, new ContactFetcher().formatPhoneNumber(phoneNumber1).length());
        assertEquals(10, new ContactFetcher().formatPhoneNumber(phoneNumber2).length());
        assertEquals(10, new ContactFetcher().formatPhoneNumber(phoneNumber3).length());
        assertEquals(10, new ContactFetcher().formatPhoneNumber(phoneNumber4).length());
        assertNull(new ContactFetcher().formatPhoneNumber(phoneNumber5));
    }
}