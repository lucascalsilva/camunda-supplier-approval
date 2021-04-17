package br.com.poc.suppliers.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.impl.persistence.entity.GroupEntity;
import org.camunda.bpm.engine.impl.persistence.entity.UserEntity;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {

    private final IdentityService identityService;
    private final ObjectMapper mapper;

    @Override
    public void run(String... args) throws Exception {
        if(identityService.createUserQuery().count() == 1) {
            Group fornecedoresGroup = new GroupEntity();
            fornecedoresGroup.setId("fornecedores");
            fornecedoresGroup.setName("Fornecedores");

            identityService.saveGroup(fornecedoresGroup);

            File usersFile = loadFile("users-fornecedores.json");
            User[] users = mapper.readValue(usersFile, UserEntity[].class);

            Arrays.stream(users).forEach(user -> {
                identityService.saveUser(user);
                identityService.createMembership(user.getId(), fornecedoresGroup.getId());
            });
        }
    }

    private File loadFile(String fileName){
        URL fileUrl = Thread.currentThread().getContextClassLoader().getResource("bootstrap" + "/" + fileName);

        if (fileUrl != null) {
            Optional<File> file = Optional.of(new File(fileUrl.getPath()));

            return file.orElseThrow(() -> new RuntimeException("File with file name "+ fileName + " not found."));

        } else {
            log.warn("Bootstrap folder not found...");
            throw new RuntimeException("File url not found for file name "+fileName);
        }
    }
}
