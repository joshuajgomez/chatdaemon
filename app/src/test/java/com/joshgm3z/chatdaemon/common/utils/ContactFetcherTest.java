package com.joshgm3z.chatdaemon.common.utils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ContactFetcherTest {

    private ContactFetcher mContactFetcher;

    @Before
    public void setUp() throws Exception {
        mContactFetcher = new ContactFetcher();
    }

    @Test
    public void cropTo10Digits() {
        String phoneNumber1 = "+917012399284";
        String phoneNumber2 = "+8190-7176-2281";
        String phoneNumber3 = "974-236-5102";
        String phoneNumber4 = "9847144525";
        String phoneNumber5 = "123";
        String phoneNumber6 = "(944) 652-0994";
        assertEquals(10, ContactFetcher.formatPhoneNumber(phoneNumber1).length());
        assertEquals(10, ContactFetcher.formatPhoneNumber(phoneNumber2).length());
        assertEquals(10, ContactFetcher.formatPhoneNumber(phoneNumber3).length());
        assertEquals(10, ContactFetcher.formatPhoneNumber(phoneNumber4).length());
        assertNull(ContactFetcher.formatPhoneNumber(phoneNumber5));
        assertEquals(10, ContactFetcher.formatPhoneNumber(phoneNumber6).length());
    }

    @Test
    public void counterProgressTest() {
        int maxCount = 500;

        int counter1 = 100;
        int counter2 = 150;
        int counter3 = 250;
        int counter4 = 350;

        System.out.println(mContactFetcher.getProgress(counter1));
        System.out.println(mContactFetcher.getProgress(counter2));
        System.out.println(mContactFetcher.getProgress(counter3));
        System.out.println(mContactFetcher.getProgress(counter4));
    }
}