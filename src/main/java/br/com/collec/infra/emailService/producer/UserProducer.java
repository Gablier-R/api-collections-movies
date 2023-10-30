package br.com.collec.infra.emailService.producer;

import br.com.collec.api.payload.emailMS.EmailDTO;
import br.com.collec.api.payload.user.UserDataDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserProducer {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Value(value = "${broker.queue.email.name}")
    private String routingKey;

    public void publishMessageEmail(UserDataDTO user){
        var emailDto = new EmailDTO();
        emailDto.setEmailTo(user.email());
        emailDto.setSubject("Cadastro realizado com sucesso!");
        emailDto.setText(user.firstName() +" "+ user.lastName() + ", seja bem vindo(a)! \nAgradecemos o seu cadastro, aproveite todos os recursos da nossa plataforma. ");

        rabbitTemplate.convertAndSend("", routingKey, emailDto);
    }
}
