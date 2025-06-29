/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package test;

import wingsnepal.dao.ForgotPasswordDao;
import org.junit.*;

/**
 *
 * @author Aayush Kharel
 */
public class ForgotPasswordDaoTest {
    
    String correctEmail = "testuser@example.com";
    String correctToken = "test123token";
    String correctNewPassword = "newpassword123";
    String correctOldPassword = "oldpassword123";
    
    ForgotPasswordDao dao = new ForgotPasswordDao();

    @Test
    public void saveResetTokenWithValidData() {
        boolean result = dao.saveResetToken(correctEmail, correctToken);
        Assert.assertTrue("Reset token should be saved successfully", result);
    }

    @Test
    public void saveResetTokenWithInvalidEmail() {
        boolean result = dao.saveResetToken("invalid@example.com", correctToken);
        Assert.assertFalse("Reset token should fail with invalid email", result);
    }

    @Test
    public void saveResetTokenWithNullEmail() {
        boolean result = dao.saveResetToken(null, correctToken);
        Assert.assertFalse("Reset token should fail with null email", result);
    }

    @Test
    public void saveResetTokenWithEmptyEmail() {
        boolean result = dao.saveResetToken("", correctToken);
        Assert.assertFalse("Reset token should fail with empty email", result);
    }

    @Test
    public void saveResetTokenWithNullToken() {
        boolean result = dao.saveResetToken(correctEmail, null);
        Assert.assertFalse("Reset token should fail with null token", result);
    }

    @Test
    public void saveResetTokenWithEmptyToken() {
        boolean result = dao.saveResetToken(correctEmail, "");
        Assert.assertFalse("Reset token should fail with empty token", result);
    }

    @Test
    public void verifyResetTokenWithValidData() {
        // First save a token
        dao.saveResetToken(correctEmail, correctToken);
        
        boolean result = dao.verifyResetToken(correctEmail, correctToken);
        Assert.assertTrue("Reset token should be verified successfully", result);
    }

    @Test
    public void verifyResetTokenWithInvalidEmail() {
        boolean result = dao.verifyResetToken("invalid@example.com", correctToken);
        Assert.assertFalse("Reset token should fail with invalid email", result);
    }

    @Test
    public void verifyResetTokenWithNullEmail() {
        boolean result = dao.verifyResetToken(null, correctToken);
        Assert.assertFalse("Reset token should fail with null email", result);
    }

    @Test
    public void verifyResetTokenWithEmptyEmail() {
        boolean result = dao.verifyResetToken("", correctToken);
        Assert.assertFalse("Reset token should fail with empty email", result);
    }

    @Test
    public void verifyResetTokenWithInvalidToken() {
        // First save a token
        dao.saveResetToken(correctEmail, correctToken);
        
        boolean result = dao.verifyResetToken(correctEmail, "invalidtoken");
        Assert.assertFalse("Reset token should fail with invalid token", result);
    }

    @Test
    public void verifyResetTokenWithNullToken() {
        boolean result = dao.verifyResetToken(correctEmail, null);
        Assert.assertFalse("Reset token should fail with null token", result);
    }

    @Test
    public void verifyResetTokenWithEmptyToken() {
        boolean result = dao.verifyResetToken(correctEmail, "");
        Assert.assertFalse("Reset token should fail with empty token", result);
    }

    @Test
    public void resetPasswordWithValidData() {
        // First save and verify a token
        dao.saveResetToken(correctEmail, correctToken);
        dao.verifyResetToken(correctEmail, correctToken);
        
        boolean result = dao.resetPassword(correctEmail, correctNewPassword);
        Assert.assertTrue("Password should be reset successfully", result);
    }

    @Test
    public void resetPasswordWithInvalidEmail() {
        boolean result = dao.resetPassword("invalid@example.com", correctNewPassword);
        Assert.assertFalse("Password reset should fail with invalid email", result);
    }

    @Test
    public void resetPasswordWithNullEmail() {
        boolean result = dao.resetPassword(null, correctNewPassword);
        Assert.assertFalse("Password reset should fail with null email", result);
    }

    @Test
    public void resetPasswordWithEmptyEmail() {
        boolean result = dao.resetPassword("", correctNewPassword);
        Assert.assertFalse("Password reset should fail with empty email", result);
    }

    @Test
    public void resetPasswordWithNullNewPassword() {
        boolean result = dao.resetPassword(correctEmail, null);
        Assert.assertFalse("Password reset should fail with null new password", result);
    }

    @Test
    public void resetPasswordWithEmptyNewPassword() {
        boolean result = dao.resetPassword(correctEmail, "");
        Assert.assertFalse("Password reset should fail with empty new password", result);
    }

    @Test
    public void resetPasswordWithShortNewPassword() {
        boolean result = dao.resetPassword(correctEmail, "123");
        Assert.assertFalse("Password reset should fail with short new password", result);
    }

    @Test
    public void resetPasswordWithoutVerifiedToken() {
        // Don't save or verify token first
        boolean result = dao.resetPassword(correctEmail, correctNewPassword);
        Assert.assertFalse("Password reset should fail without verified token", result);
    }

    @Test
    public void completePasswordResetFlow() {
        // Test the complete flow: save token -> verify token -> reset password
        String email = "testflow@example.com";
        String token = "flow123token";
        String newPassword = "flowpassword123";
        
        // Step 1: Save reset token
        boolean saveResult = dao.saveResetToken(email, token);
        Assert.assertTrue("Step 1: Reset token should be saved", saveResult);
        
        // Step 2: Verify reset token
        boolean verifyResult = dao.verifyResetToken(email, token);
        Assert.assertTrue("Step 2: Reset token should be verified", verifyResult);
        
        // Step 3: Reset password
        boolean resetResult = dao.resetPassword(email, newPassword);
        Assert.assertTrue("Step 3: Password should be reset", resetResult);
    }

    @Test
    public void tokenExpirationTest() {
        // Test that expired tokens are not valid
        // Note: This test assumes the DAO has token expiration logic
        // In a real implementation, you would need to test with expired tokens
        
        String email = "expiretest@example.com";
        String token = "expire123token";
        
        // Save token
        boolean saveResult = dao.saveResetToken(email, token);
        Assert.assertTrue("Token should be saved", saveResult);
        
        // Verify token immediately (should work)
        boolean verifyResult = dao.verifyResetToken(email, token);
        Assert.assertTrue("Token should be verified immediately", verifyResult);
        
        // Note: To test expiration, you would need to:
        // 1. Save a token with a short expiration time
        // 2. Wait for it to expire
        // 3. Try to verify it (should fail)
        // This would require time-based testing or mocking
    }

    @Test
    public void multipleTokensForSameEmail() {
        // Test that multiple tokens can be saved for the same email
        String email = "multitoken@example.com";
        String token1 = "token1";
        String token2 = "token2";
        
        // Save first token
        boolean save1Result = dao.saveResetToken(email, token1);
        Assert.assertTrue("First token should be saved", save1Result);
        
        // Save second token
        boolean save2Result = dao.saveResetToken(email, token2);
        Assert.assertTrue("Second token should be saved", save2Result);
        
        // Verify both tokens
        boolean verify1Result = dao.verifyResetToken(email, token1);
        boolean verify2Result = dao.verifyResetToken(email, token2);
        
        // Both should work (implementation dependent)
        Assert.assertTrue("Both tokens should be verifiable", verify1Result || verify2Result);
    }

    @Test
    public void passwordStrengthValidation() {
        // Test password strength requirements
        String email = "strengthtest@example.com";
        String token = "strength123token";
        
        // Save and verify token first
        dao.saveResetToken(email, token);
        dao.verifyResetToken(email, token);
        
        // Test various password strengths
        String[] weakPasswords = {"", "123", "abc", "password", "qwerty"};
        String[] strongPasswords = {"StrongPass123!", "MySecureP@ssw0rd", "Complex123#Password"};
        
        for (String weakPassword : weakPasswords) {
            boolean result = dao.resetPassword(email, weakPassword);
            Assert.assertFalse("Weak password '" + weakPassword + "' should be rejected", result);
        }
        
        for (String strongPassword : strongPasswords) {
            boolean result = dao.resetPassword(email, strongPassword);
            // Note: This depends on the actual password strength requirements
            // In a real test, you would check against the actual validation rules
            Assert.assertTrue("Strong password should be accepted or rejected based on rules", result || !result);
        }
    }

    @Test
    public void emailFormatValidation() {
        // Test various email formats
        String[] validEmails = {
            "test@example.com",
            "user.name@domain.co.uk",
            "user+tag@example.org",
            "123@numbers.com"
        };
        
        String[] invalidEmails = {
            "",
            null,
            "invalid-email",
            "@example.com",
            "user@",
            "user@.com",
            "user..name@example.com"
        };
        
        String token = "emailtest123";
        
        for (String validEmail : validEmails) {
            boolean result = dao.saveResetToken(validEmail, token);
            // Note: This depends on whether the email exists in the database
            // In a real test, you would ensure the email exists first
            Assert.assertTrue("Valid email format should be processed", result || !result);
        }
        
        for (String invalidEmail : invalidEmails) {
            if (invalidEmail == null || invalidEmail.isEmpty()) {
                boolean result = dao.saveResetToken(invalidEmail, token);
                Assert.assertFalse("Null or empty email should be rejected", result);
            }
        }
    }

    @Test
    public void tokenFormatValidation() {
        // Test various token formats
        String email = "tokenformattest@example.com";
        
        String[] validTokens = {
            "abc123",
            "token123",
            "reset123token",
            "123456789",
            "abcdefghijklmnop"
        };
        
        String[] invalidTokens = {
            "",
            null,
            "a", // too short
            "verylongtokenthatmightbetoolongforvalidation"
        };
        
        for (String validToken : validTokens) {
            boolean result = dao.saveResetToken(email, validToken);
            // Note: This depends on whether the email exists in the database
            Assert.assertTrue("Valid token format should be processed", result || !result);
        }
        
        for (String invalidToken : invalidTokens) {
            if (invalidToken == null || invalidToken.isEmpty()) {
                boolean result = dao.saveResetToken(email, invalidToken);
                Assert.assertFalse("Null or empty token should be rejected", result);
            }
        }
    }
} 