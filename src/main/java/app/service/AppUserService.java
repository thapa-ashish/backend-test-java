package app.service;

import app.entity.AppUser;
import app.repository.AppUserRepository;

public class AppUserService {

    private final AppUserRepository appUserRepository;

    public AppUserService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }


    public AppUser saveAppUser(AppUser appUser){
        return appUserRepository.saveAppUser(appUser);
    }

}
