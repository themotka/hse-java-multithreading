# Java multithreading lab
## Задание:
Есть многоэтажный жилой дом, в нем 2 лифта. Рандомно с разных этажей поступают вызовы лифта. 
Лифт может останавливаться по попутным вызовам. В себе программа содержит реализацию симуляции 
вызова лифтов людьми (в одном потоке) и движение лифтов, минимизирующее время ожидания лифта
после поступления вызова и пробег лифта (в двух других потоках).

## Описание:
Тз реализовано в виде двух лифтов, двигающихся по многоэтажному дому (количество этажей меняется при помощи константы
`TOTAL_FLOORS`, каждый лифт на этаже имеет одну кнопку вызова. Логика выбора лифта инкапсулирована в классе `PeopleFactory`,
являющимся генератором людей (Person):
```
if (!elevator2.isBusy() && elevator1.isBusy()) {
	callElevator(person, elevator2);
} else if (elevator2.isBusy() && !elevator1.isBusy()) {
	callElevator(person, elevator1);
} else if (elevator2.isBusy() && elevator1.isBusy() || !elevator2.isBusy() && !elevator1.isBusy()) {
	if (Math.abs(elevator1.getCurrFloor() - person.getCurrFloor()) < Math.abs(elevator2.getCurrFloor() - person.getCurrFloor())) {
		callElevator(person, elevator1);
	} else callElevator(person, elevator2);
}
```
----
Поля пассажира, доступ к которым осуществляется геттерами:
```
private final int currFloor;
private final int destFloor;
```
----
Сами лифты содержат в себе две LinkedBlockingQueue, хранящие людей внутри лифта и ожидающих приезда соответственно.
```
BlockingQueue<Person> inside = new LinkedBlockingQueue<>();
BlockingQueue<Person> outside = new LinkedBlockingQueue<>();
```
При этом если пассажир внутри лифта, 
его пункт назначения достигается вне зависимости от вызовов внешних пассажиров, но по путии лифт открывает двери для всех ожидающих.
Таким образом достигается минимизация времени достижения каждым пассажиром целевого этажа.
