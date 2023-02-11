package ru.meklaw.autodrome.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.meklaw.autodrome.controllers.rest.ReportRestController;
import ru.meklaw.autodrome.dto.GenerateReport;
import ru.meklaw.autodrome.dto.Period;
import ru.meklaw.autodrome.dto.ReportType;
import ru.meklaw.autodrome.models.Manager;
import ru.meklaw.autodrome.models.Person;
import ru.meklaw.autodrome.repositories.ManagerRepository;
import ru.meklaw.autodrome.repositories.PersonRepository;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
// TODO: 11.02.2023 REFACTOR
public class TelegramBot extends TelegramLongPollingBot {
    private final AuthenticationManager authenticationManager;
    private final PersonRepository personRepository;
    private final ManagerRepository managerRepository;
    private final ReportRestController reportRestController;

    @Value("${telegram-bot.name}")
    private String botUsername;

    @Value("${telegram-bot.token}")
    private String botToken;

    private final Map<Long, Boolean> session = new HashMap<>();
    private final Map<Long, Manager> manager = new HashMap<>();


    @Autowired
    public TelegramBot(AuthenticationManager authenticationManager, PersonRepository personRepository,
                       ManagerRepository managerRepository, ReportRestController reportRestController) {
        this.authenticationManager = authenticationManager;
        this.personRepository = personRepository;
        this.managerRepository = managerRepository;
        this.reportRestController = reportRestController;
        List<BotCommand> commands = new ArrayList<>();
        commands.add(new BotCommand("/start", "get start"));
        commands.add(new BotCommand("/login", "get login"));
        try {
            this.execute(new SetMyCommands(commands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {

        }
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage()) {
            return;
        }

        long chatId = update.getMessage()
                            .getChatId();
        String message = update.getMessage()
                               .getText();

        if (isStartCommand(message)) {
            handleStartCommand(chatId);
            return;
        }

        if (isLoginCommand(message)) {
            handleLoginCommand(chatId);
            return;
        }

        if (!isSessionActive(chatId)) {
            handleAuthentication(chatId, message);
            return;
        }

        if (message.equals("/report")) {
            sendMessage(chatId, "Please enter the report information in the following format: " +
                    "report: 2 CAR MONTH 2023-02-11T00:37:26.422294Z 2023-02-26T18:14:29.422294Z");
            return;
        }


        if (message.startsWith("report: ")) {
            String[] parts = message.split(" ");
            if (parts.length == 6) {
                long id = Long.parseLong(parts[1]);
                ReportType type = ReportType.valueOf(parts[2]);
                Period period = Period.valueOf(parts[3]);
                ZonedDateTime startTime = ZonedDateTime.parse(parts[4],
                        DateTimeFormatter.ISO_DATE_TIME);
                ZonedDateTime endTime = ZonedDateTime.parse(parts[5],
                        DateTimeFormatter.ISO_DATE_TIME);

                GenerateReport generateReport = new GenerateReport(id, type, period, startTime, endTime);
                String sss = reportRestController.generateReport(generateReport)
                                               .toString();
                sendMessage(chatId, sss);
                return;
            }
        }

        sendMessage(chatId, "Please check your string.");
    }

    private boolean isStartCommand(String message) {
        return message.equals("/start");
    }

    private boolean isLoginCommand(String message) {
        return message.equals("/login");
    }

    private boolean isSessionActive(long chatId) {
        return session.containsKey(chatId) && !session.get(chatId);
    }

    private void handleStartCommand(long chatId) {
        sendMessage(chatId, "Hello! To access the management functionality, please log in.");
    }

    private void handleLoginCommand(long chatId) {
        if (isSessionActive(chatId)) {
            sendMessage(chatId, "You are already logged in.");
        } else {
            sendMessage(chatId, "Please enter your login and password.");
            session.put(chatId, true);
        }
    }

    private void handleAuthentication(long chatId, String message) {
        String[] loginAndPassword = message.split(" ");
        if (loginAndPassword.length != 2) {
            sendMessage(chatId, "Invalid input. Please enter your login and password separated by a space.");
        } else {
            String login = loginAndPassword[0];
            String password = loginAndPassword[1];
            Person person = personRepository.findByUsername(login)
                                            .orElse(null);
            if (person == null) {
                sendMessage(chatId, "Invalid login or password.");
            } else {
                try {
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, password));
                    session.put(chatId, false);
                    manager.put(chatId, managerRepository.findByUsername(login)
                                                         .orElseThrow());
                    sendMessage(chatId, "You have been successfully logged in.");
                } catch (BadCredentialsException e) {
                    sendMessage(chatId, "Invalid login or password.");
                }
            }
        }
    }

    private void sendMessage(long chatId, String message) {
        SendMessage sendMessage = SendMessage.builder()
                                             .chatId(String.valueOf(chatId))
                                             .text(message)
                                             .build();
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}


