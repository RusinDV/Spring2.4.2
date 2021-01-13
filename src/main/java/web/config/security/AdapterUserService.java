package web.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import web.model.AuthGroup;
import web.model.User;
import web.service.UserService;

import java.util.List;

@Service
public class AdapterUserService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userService.redUserByName(s);
        List<AuthGroup> authGroupList= user.getAuthGroupList();
        return new AdapterUserDetails(user,authGroupList);
    }
}
