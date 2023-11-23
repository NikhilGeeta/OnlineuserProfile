package com.OnlineApplication.Repository;

import com.OnlineApplication.Entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface userRepo extends JpaRepository<UserProfile,Long> {

}
