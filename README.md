# Стани богат

Това е следващото голямо упражнение, което правим на школата за Мобилни приложения в ПЧМГ.
Целта е да направим приложение "Стани богат", с което потребителят може да играе известната игра. 

# Дизайн
Под дизайн имаме предвид не само графичен дизайн, а и дизайн на екраните, дизайн на правилата на играта, дизайн на кода. 
## Склет на екраните
Първото нещо което трябва да направим е да уточним как ще изглежда нашето приложение. Решихме да работи ориентирано хоризонтално (landscape).

![Wireframes](./wireframe.png "Wireframe")


1. Начален екран, от който можем да пуснем играта, да видим най-добрия резултат до момента и въведение в това как се играе
2. Екран с въпрос. 
  1. На него виждаме въпроса с четирите възомжни отговора. 
  1. Имаме три жокера - 50:50, помощ от публиката и смяна на въпроса. 
  2. На този екран ще се показва също така на кой от въпросите сме. Напр. въпрос 3 от 15. 
  3. Ще можем да споделим въпроса в социална медия.
  4. Ще можем да се откажем.
  5. При избор на отговор ще излиза диалогов прозорец, който приканва потребителя да потвърди решението си
1. Финален екран
  1. показваме резултата "Ти спечели ..."
  2. Бутон за споделяне на резултата в социална медия
  3. Бутон за започнаве на нова игра
  4. Бутон за отиване в началото

## Правила
1. Всеки от жокерите ще може да се използва по веднъж;
2. Ако играчът се откаже на въпрос взима сумата от миналия въпрос;
3. При грешен отговор взима последната сигурна сума (сумата от 5тия или 10тия въпрос)

## Изисквания към кода
Към софтуера ще имаме следните изисквания:

1. Въпросите да са разделени в три категории - лесни (от където ще се избират въпроси от 1 до 5), средни (за въпроски от 5 до 10) и трудни (за въпроси от 10 до 15).
2. Въпросите от всяка категория се избират по случаен начин
3. Добавянето на нови въпроси да става лесно.

# Да направим потребителския интерфейс

## Хоризонтална ориентация (landscape)
Дори и да не знам как да направя приложението да работи само в хоризонтален режим мога да го разбера лесно като потъся в гугъл "android landscape oreintation only".  Първият резултат сочи към [този пост](http://stackoverflow.com/questions/8408197/android-landscape-only-orientation)

Изисква се да добавим ```android:screenOrientation="landscape"``` към всяко Activity.
Така ще имаме например
```
<activity
    android:name=".MainActivity"
    android:screenOrientation="landscape">
```

### Как да завъртим емулатора хоризонтално
Търсим в гугъл ```android emulator landscape``` и първият резултат е [това](http://stackoverflow.com/questions/2618967/switching-to-landscape-mode-in-android-emulator).
Т.е. с Ctr + F11 можем да завъртаме емулатора.


## Как бутоните за възможни отговори да променят вида си като си кликнати?
За целта ще използваме селектор (selector). 
Нека имаме следния бутон "Pesho" в xml-а на активитито.
```xml
<Button
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Pesho"
        android:background="@drawable/pesho_selector"
        android:onClick="peshoClicked"/>
```
Също в папката drawable имаме файла ```pesho_selector.xml``` със съдържание:
```xml
<?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://schemas.android.com/apk/res/android">

    <item android:drawable="@drawable/pesho_pressed"
        android:state_selected="true"></item>

    <item android:drawable="@drawable/pesho_released"
        android:state_selected="false"></item>

</selector>
```
И също в папката drawable имаме две картиники pesho_pressed.png и pesho_released.png - първата е картинката на избран бутон, а втората - на неизбран бутон.

И в activity-то, в което е бутона имаме:
```java
public void peshoClicked(View v) {
    if(v.isSelected()) {
        v.setSelected(false);
    } else {
        v.setSelected(true);
    }
}
```

Сега ще се случва следното: Когато опрационната система Андроид се чуди кой фон (backgrond) да сложи на бутона ще отвори файлът ```pesho_selector``` и там от там, ще види ще ако състоянието (state) на бутона е selected трябва да покаже картинката ```@drawable/pesho_pressed"```. Това става с кода
```xml
 <item android:drawable="@drawable/pesho_pressed"
        android:state_selected="true"></item>
```
В противен случай трябва да покаже картинката ```@drawable/pesho_released```

Когато потребителят кликне бутона ще се включи фукнцията ```peshoClicked(View v)```, която ще провери дали бутонът Пешо е селектиран. Ако не е селектиран ще го селектира, и обратното. Т.е. като се кликне на бутона ще се промени състението му от селектиран на неселектиран и обратното.



