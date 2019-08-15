package com.github.xuqplus2.blog.util;

import com.github.xuqplus2.blog.domain.User;
import com.github.xuqplus2.blog.repository.UserRepository;
import com.github.xuqplus2.blog.vo.resp.CurrentUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static com.github.xuqplus2.blog.domain.User.USER_INFO_EXPIRED_MILLS;

public class CurrentUserUtil {

    public static final CurrentUser getCurrentUser() throws AppNotLoginException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (null != authentication && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof CurrentUser) {
                return (CurrentUser) principal;
            }
        }
        throw new AppNotLoginException();
    }

    public static final User currentUser(UserRepository userRepository) throws AppNotLoginException {
        CurrentUser currentUser = getCurrentUser();
        Optional<User> optional = userRepository.findById(currentUser.getUserId());
        if (optional.isPresent()) {
            User user = optional.get();
            if (null == user.getUpdateAt() || user.getUpdateAt() - System.currentTimeMillis() > USER_INFO_EXPIRED_MILLS) {
                user.update(currentUser);
                userRepository.save(user);
            }
            return user;
        }
        User user = new User(currentUser);
        user.setUpdateAt(System.currentTimeMillis());
        userRepository.save(user);
        return user;
    }
}
