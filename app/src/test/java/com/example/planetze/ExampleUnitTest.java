package com.example.planetze;

import org.junit.Test;

import com.example.planetze.models.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.function.Consumer;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;


import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Mock
    private Model model;
    @Mock
    private MainActivity view;
    //private Presenter presenter;
    //separate into two instead of as a field
    @Test
    public void testSuccessfulLogin() {
        // Arrange
        String email = "a@mail.com";
        String password = "123456";
        Presenter presenter = new Presenter(model,view);
        //update score field when nioki merge
        User user = new User("userId", "username", "a@mail.com",0.0);
        // Mock the authenticate method to simulate successful login
        doAnswer(invocation -> {
            Consumer<User> callback = invocation.getArgument(2);
            callback.accept(user);
            return null;
        }).when(model).authenticate(eq(email), eq(password), any());
        // Act
        presenter.login(email, password);
        // Assert
        verify(view).redirectToDashboard("userId");
    }
    @Test
    public void testFailedLogin() {
        // Arrange
        String email = "kevin@mail.com";
        String password = "123456";
        Presenter presenter = new Presenter(model,view);
        // Mock the authenticate method to simulate failed login
        doAnswer(invocation -> {
            Consumer<User> callback = invocation.getArgument(2);
            callback.accept(null);
            return null;
        }).when(model).authenticate(eq(email), eq(password), any());
        // Act
        presenter.login(email, password);
        // Assert
        verify(view).failedToLogin();
    }
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
}