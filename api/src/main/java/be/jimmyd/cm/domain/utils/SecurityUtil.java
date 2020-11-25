package be.jimmyd.cm.domain.utils;

import org.springframework.stereotype.Component;

@Component
public class SecurityUtil {

    public boolean hasUserReadAccessToCollection(String mail, long collectionId) {
        //TODo add logic
        return true;
    }
}
