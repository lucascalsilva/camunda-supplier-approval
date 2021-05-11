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
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {

    private final IdentityService identityService;
    private final ObjectMapper mapper;
    private final ResourceLoader resourceLoader;

    @Override
    public void run(String... args) throws Exception {
        if (identityService.createUserQuery().count() == 1) {
            Group fornecedoresGroup = new GroupEntity();
            fornecedoresGroup.setId("fornecedores");
            fornecedoresGroup.setName("Fornecedores");

            identityService.saveGroup(fornecedoresGroup);

            InputStream fileStream = loadFile("users-fornecedores.json");
            User[] users = mapper.readValue(fileStream, UserEntity[].class);

            Arrays.stream(users).forEach(user -> {
                identityService.saveUser(user);
                identityService.createMembership(user.getId(), fornecedoresGroup.getId());
            });
        }
    }

    private InputStream loadFile(String fileName) throws IOException {
        //Optional<File> file = Optional.of(new File(getClass().getResource("bootstrap" + "/" + fileName).getFile()));
        Resource resource = resourceLoader.getResource("classpath:bootstrap" + "/" + fileName);
        return resource.getInputStream();
    }
}
