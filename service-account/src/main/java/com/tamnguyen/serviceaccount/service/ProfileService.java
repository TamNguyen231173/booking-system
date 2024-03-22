package com.tamnguyen.serviceaccount.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tamnguyen.serviceaccount.DTO.Account.UpdateProfile;
import com.tamnguyen.serviceaccount.model.Account;
import com.tamnguyen.serviceaccount.model.Profile;
import com.tamnguyen.serviceaccount.repository.ProfileRepository;

@Service
public class ProfileService {
   @Autowired
    private ProfileRepository profileRepository;

    public Profile findOneAndUpdate(Account account,UpdateProfile body) {
        if (account == null || body == null) {
            throw new IllegalArgumentException("account and body must not be null");
        }

        Profile profile = profileRepository.findByAccountId(account.getId());

        if (profile == null) {
            profile = new Profile();
            profile.setAccount(account);
        }

        BeanUtils.copyProperties(body, profile);
        profileRepository.save(profile);

        return profile;
    }
}
