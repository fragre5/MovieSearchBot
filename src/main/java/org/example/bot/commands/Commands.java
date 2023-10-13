package org.example.bot.commands;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.kpfinder.Finder;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@Slf4j
@AllArgsConstructor
public class Commands {

    private Update update;

    private String getFirstName() {
        return update.getMessage().getChat().getFirstName();
    }

    public String startCommand() {
        log.info("Init Start Command!");
        return "Привет, " + getFirstName() + "! \n" +
                "Этот бот предназначен для поиска фильмов и сериалов.\n" +
                "Вы можете написать название и бот вернёт вам ссылку на просмотр этого фильма.\n" +
                "В будущем он будет способен выдавать вам ссылки на торрент, для скачивания фильма.";
    }

    public String findMovie(String nameOfFilm) {

        log.info("Init FindMovie Command!");


        Optional<String> filmURL = Finder.getMovieURL(nameOfFilm);

        return filmURL.get().replace(Constants.origin_ru_tld, Constants.pathfinder_common_tld);
    }

    public String unknownCommand(String unknownCommand) {
        return unknownCommand + " - данной команды не существует.";
    }

}
