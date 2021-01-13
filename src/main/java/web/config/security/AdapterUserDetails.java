package web.config.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import web.model.AuthGroup;
import web.model.User;

import java.util.*;


public class AdapterUserDetails implements UserDetails{

    private User user;
    private List<AuthGroup> authGroupList;

    public AdapterUserDetails(User user, List<AuthGroup> authGroupList) {
        super();
        this.user = user;
        this.authGroupList=authGroupList;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> grantedAuthoritySet=new HashSet<>();
        if(authGroupList==null){
            return Collections.emptySet();
        }
        for (AuthGroup authGroup : authGroupList) {
            grantedAuthoritySet.add(new SimpleGrantedAuthority(authGroup.getAuthgroup()));
        }
       return grantedAuthoritySet;

    }

    @Override
    public String getPassword() {        return user.getPassword();    }

    @Override
    public String getUsername()  {      return user.getName();        }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {      return true;    }
}
