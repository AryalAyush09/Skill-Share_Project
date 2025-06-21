package com.project.skill_share.enums;

public enum OtpPurpose {
    VERIFY_EMAIL("Verify your email address", """
        Hello {USERNAME},

        Use the following OTP to verify your email: {OTP}

        This OTP is valid for 5 minutes. Please do not share it with anyone.

        Regards,  
        SkillShare Team
    """),

    RESET_PASSWORD("Reset your password", """
        Hi {USERNAME},

        You requested a password reset. Your OTP is: {OTP}

        This OTP will expire in 5 minutes.

        If this wasn’t you, please ignore this email.

        Thanks,  
        SkillShare Security Team
    """);

    private final String subject;
    private final String template;

    OtpPurpose(String subject, String template) {
        this.subject = subject;
        this.template = template;
    }

    public String getSubject() {
        return subject;
    }

    public String getFormattedMessage(String username, String otp) {
        return template
                .replace("{USERNAME}", username)
                .replace("{OTP}", otp);
    }
}
