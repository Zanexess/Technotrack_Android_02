﻿# Technotrack_Android_02
Написать приложение отображающее список технологий из игры "Эволюция: Битва за Утопию" с сплеш скрином из первого ДЗ.
Первое активити - наш старый знaкомый сплеш скрин, только показывается не больше чем время загрузки или 2 секунды (что дольше). Второе активити представляет собой список и ViewPager сформированный из этого JSON файла
Это JSON файлу - массив "технологий" в игре Эволюция.
В каждой технологии вас должны интересовать поля
- picture - относительный (mobevo.ext.terrhq.ru/) URL картинки
- title - название
- info - дополнительная информация (может не быть)


Второе активити должно начинаться с фрагмента со списком, в элементе списка
маленькая картинка 64dp и название.
Список кликабельный при клике на технологии открывается ViewPager c фрагментом
в котором соответствующая картинка большого размера (не больше ширины экрана-20dp) и описанием технологии, при свайпе вправо описание должно меняться на описание предмета выше в списке, соотвественно влево - на описание ниже в списке.
Более подробно про ViewPager
Опционально приложение может предоставлять Content Provider с данными технологий.

Загрузка JSON должна происходить в момент показа сплеш скрина. Загрузка картинок, в процессе показа списка (естественно не в UI потоке), имена технологий не должны прыгать в процессе загрузки. Необходимо минимизировать использование трафика.

# Все решения должны:
- быть в виде проекта на GitHub
- запускаться на вашем устройстве/эмуляторе для демострации
- работать на минимальной версии 15
- собираться из консоли gradle

# Максимальный бал за задачу - 17.
Оценка ставится по следующим критериям:
- 1-3: вы хотя бы пытались
- 3-7: приложение содержит большую часть решения задачи, но падает или работает некорректно
- 7-17: приложение работает как должно в нормальных условиях, не падает в большинстве случаев, имеет мелкие огрехи

# Сроки для сдачи первого ДЗ:
Задача сдана до 27.04.2016 00:00 - Мы даем фидбек на решение, даем оценку, и она записывается в ведомость
Задача сдана после 27.04.2016 00:00 - Мы даем фидбек на решение, даем оценку, но она записывается в ведомость с 75% "штрафом"

При разработке домашних заданий мы не используем сторонние библиотеки (за исключением support) и не используем чужой код, если это не обговорено отдельно. Все задания даются так, чтобы они были выполнимы самостоятельно. Вам никто не мешает использовать чужой код, как пример для обучения, но не надо его вставлять в код ДЗ.