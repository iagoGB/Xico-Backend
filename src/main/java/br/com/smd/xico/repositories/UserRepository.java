package br.com.smd.xico.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.smd.xico.models.UserModel;

public interface UserRepository extends JpaRepository<UserModel, Long> {

}
