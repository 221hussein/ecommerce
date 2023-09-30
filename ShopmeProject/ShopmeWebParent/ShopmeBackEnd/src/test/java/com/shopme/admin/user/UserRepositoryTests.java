package com.shopme.admin.user;

import com.shopme.admin.user.repository.UserRepository;
import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TestEntityManager entityManager;
    @Test
    public void testCreateUser() {
        Role roleAdmin = entityManager.find(Role.class,1);
        User userHussein = new User("ousseynou@gmail.com","test123","hussein","thiaw");
        userHussein.addRole(roleAdmin);

        User savedUser = userRepository.save(userHussein);
        assertThat(savedUser.getId()).isGreaterThan(0);
    }
    @Test
    public void testCreateNewUserWithTwoRoles() {

        User userMamadou = new User("Mamadou@gmail.com","test123","Mamadou","thiaw");
        Role roleSalesPerson = new Role(3);
        Role roleAssistant = new Role(5);
        userMamadou.addRole(roleSalesPerson);
        userMamadou.addRole(roleAssistant);

        User savedUser = userRepository.save(userMamadou);
        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAllUsers() {
        Iterable<User> listUsers = userRepository.findAll();
        listUsers.forEach(System.out::println);
    }

    @Test
    public void testGetUserById() {
        User userHussein = userRepository.findById(1).get();
        System.out.println(userHussein);
        assertThat(userHussein).isNotNull();
    }

    @Test
    public void testUpdateUserDetails() {
        User userHussein = userRepository.findById(1).get();

        userHussein.setEnabled(true);
        Role roleAdmin = new Role(2);
        userHussein.addRole(roleAdmin);

        userRepository.save(userHussein);
    }

    @Test
    public void testUpdateUserRoles() {
        User userMamadou = userRepository.findById(3).get();

        Role roleSalesPerson = new Role(3);
        Role roleEditor = new Role(4);

        userMamadou.getRoles().remove(roleSalesPerson);
        userMamadou.addRole(roleEditor);

        userRepository.save(userMamadou);
    }

    @Test
    public void testDeleteUser() {
        Integer userId = 3;
        userRepository.deleteById(userId);
    }

    @Test
    public void testGetUserByEmail() {
        String email = "user2@gmail.com";
        User user = userRepository.getUserByEmail(email);

        assertThat(user).isNotNull();
    }

    @Test
    public void testCountById() {
        Integer id = 1;
        Long countById = userRepository.countById(id);

        assertThat(countById).isNotNull().isGreaterThan(0);
    }

    @Test
    public void testDisabledUser() {
        Integer id = 1;
        userRepository.updateEnabledStatus(id, false);

    }

    @Test
    public void testEnableUser() {
        Integer id = 1;
        userRepository.updateEnabledStatus(id, false);
    }

    @Test
    public void testListFirstPage() {
        int pageNumber= 0;
        int pageSize = 4;
        Pageable pageable = PageRequest.of(pageNumber,pageSize);
        Page<User> page = userRepository.findAll(pageable);

        List<User> listUsers = page.getContent();
        listUsers.forEach(System.out::println);
        assertThat(listUsers.size()).isEqualTo(pageSize);
    }

    @Test
    public void testSearchUsers() {
        String keyword = "bruce";

        int pageNumber= 0;
        int pageSize = 4;
        Pageable pageable = PageRequest.of(pageNumber,pageSize);
        Page<User> page = userRepository.findAll(keyword,pageable);

        List<User> listUsers = page.getContent();
        listUsers.forEach(System.out::println);


        assertThat(listUsers.size()).isGreaterThan(0);

    }
}
