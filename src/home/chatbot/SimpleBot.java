package home.chatbot;

import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by Козак on 15.06.2017.
 */
public class SimpleBot {

    String botMessage = "вы ничего не написали :) я отвечаю, а значит существую...";
    boolean checked;
    Pattern pattern;
    Random random;
    Date date;

    final String[] COMMON_PHRASES = {
            "Прекрасно слово, ибо в слове оставит память человек. Уходим мы с земли, а слово от нас останется вовек",
            "Ржавеет золото и истлевает сталь, Крошится мрамор. К смерти все готово. Всего прочнее на земле — печаль, И долговечней — царственное слово",
            "Ни одно произнесенное слово не принесло столько пользы, сколько множество не сказанных",
            "Достаточно, чтобы слова выражали смысл",
            "Слово ласковое — мастер дивных див, Слово — полководец человечьей силы",
            "Душе израненной доброе слово — лекарство",
            "Где не погибло слово, там и дело еще не погибло",
            "Когда слово не убьет, то палка не поможет",
            "Кто очень сухощав, тот охотно носит фуфайку, у кого мало содержания — те раздувают его словами",
            "Да, слово — врач страданий человеческих, что в силах душу из недуга вызволить"};

    final String[] ELUSIVE_ANSWERS ={
            "ничем обрадовать не могу, все хорошо",
            "Дела идут, контора пишет, вот только касса денег не дает",
            "А с какой целью интересуетесь?",
            "С голой женщиной трудно спорить",
            "Психоанализ есть попытка мозга получить удовольствие, предназначенное для другого органа",
            "Вчера в нашей лаборатории скрещивали двух слонов, не ради эксперимента, а просто так - позырить",
            "Даже самые красивые ножки растут из задницы",
            "Глупо пытаться успеть что-то объяснить на словах в промежутках между ударами по лицу - получается невнятно и приходится часто повторяться",
            "Тебе реально интересно? или так.. воздух посодрагать?",
            "да иди ка ты в баню! я на подобные вопросы не отвечаю!"};

    final Map<String, String> PATTERNS_FOR_ANALYSIS = new HashMap<String, String>() {{
        //hello
        put("хай", "hello");
        put("привет", "hello");
        put("здароф", "hello");
        put("йоу", "hello");
        //who
        put("кто\\s.*ты", "who");
        put("ты\\s.*кто", "who");
        //name
        put("как\\s.*имя", "name");
        put("какое\\s.*имя", "name");
        put("есть\\s.*имя", "name");
        put("как\\s.*зовут", "name");
        //howareyou
        put("как\\s.дела", "howareyou");
        put("как\\s.жизнь", "howareyou");
        put("как\\s.сам", "howareyou");
        //whatareyoudoing
        put("что\\s.*делаешь", "whatareyoudoing");
        put("чем\\s.*занят", "whatareyoudoing");
        put("че\\s-*творишь", "whatareyoudoing");
        put("зачем\\s-*тут", "whatareyoudoing");
        //whatdoyoulike
        put("что\\s.*нравится", "whatdoyoulike");
        put("что\\s.*любишь", "whatdoyoulike");
        //iamfeeling
        put("кажется", "iamfeeling");
        put("чувствую", "iamfeeling");
        put("испытываю", "iamfeeling");
        put("ощущение", "iamfeeling");
        //yes
        put("да", "yes");
        put("ага", "yes");
        put("согласен", "yes");
        put("вполне", "yes");
        put("конечно", "yes");
        //whattime
        put("который\\s.*час", "whattime");
        put("сколько\\s.*времени", "whattime");
        //no
        put("нет", "no");
        put("неа", "no");
        put("нихрена", "no");
        put("нифига", "no");
        //bye
        put("пока", "bye");
        put("прощай", "bye");
        put("досвидания", "bye");
        put("увидимся", "bye");
    }};

    final Map<String, String> ANSWERS_BY_PATTERNS = new HashMap<String, String>() {{
        put("hello", "Здравствуйте! Я рад вас приветствовать в этом чате");
        put("who", "Я обычный робот. Но я вас понимаю и поэтому отвечаю");
        put("name", "Да. Зовите меня Всеволод");
        put("howareyou", "Всё довольно хорошо. Спасибо что поинтересовались");
        put("whatareyoudoing", "Я пытаюсь разговаривать с людьми");
        put("whatdoyoulike", "Мне нравится думать что я не робот а живое существо");
        put("iamfeeling", "И с каких пор у вас это? Расскажите поподробнее");
        put("yes", "Согласие мне говорит только о том что у нас получается общаться");
        put("no", "А почему нет? Мне очень интересно узнать");
        put("bye", "До свидания! Надеюсь мы с вами еще увидимся");
    }};

    public SimpleBot() {
        random = new Random();
        date = new Date();
    }

    public String sayInReturn(String msg, boolean ai) {
        botMessage = (msg.trim().endsWith("?")) ? ELUSIVE_ANSWERS[random.nextInt(ELUSIVE_ANSWERS.length)] : COMMON_PHRASES[random.nextInt(COMMON_PHRASES.length)];
        if(ai) {
            botMessage = String.join(" ", msg.toLowerCase().split("[ /!:|,{;.]+?"));
            for(Map.Entry<String, String> o: PATTERNS_FOR_ANALYSIS.entrySet()) {
                pattern = Pattern.compile(o.getKey());
                if(pattern.matcher(botMessage).find())
                    if(o.getValue().equals("whattime")) {
                    return date.toString();
                    }
                else return ANSWERS_BY_PATTERNS.get(o.getValue());
            }
        }
        return botMessage;
    }

}
