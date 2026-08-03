package com.hritik.portfolio.dto.response;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private String errorDetails;

    // Helper method for successful responses
    public static <T> ApiResponse<T> success(String message, T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .build();
    }

    // Helper method for error responses
    public static <T> ApiResponse<T> error(String message, String errorDetails) {
        return ApiResponse.<T>builder()
                .success(false)
                .message(message)
                .errorDetails(errorDetails)
                .build();
    }
}