import java.io.FileWriter;
import java.sql.Time;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

public class Main {
    /*
    Подумать над структурой класса Ноутбук для магазина техники - выделить поля и методы. Реализовать в java.
    Создать множество ноутбуков.
    Написать метод, который будет запрашивать у пользователя критерий (или критерии) фильтрации и выведет ноутбуки,
    отвечающие фильтру. Критерии фильтрации можно хранить в Map. Например:
            “Введите цифру, соответствующую необходимому критерию:
            1 - ОЗУ
            2 - Объем ЖД
            3 - Операционная система
            4 - Цвет …
    Далее нужно запросить минимальные значения для указанных критериев - сохранить параметры
    фильтрации можно также в Map.
    Отфильтровать ноутбуки из первоначального множества и вывести подходящие по условиям.
     */

    public static void main(String[] args) {
        Date time = new Date(System.currentTimeMillis());
        Set<Config> laptops = new HashSet<>();
        Set<Config> infoBase = new HashSet<>();
        Random random = new Random();
        String nameStore = "RTX-Life";
        int minNum = 15;
        int maxNum = 41;
        int count = random.nextInt(maxNum - minNum) + minNum;
        System.out.println("\t\t\t Добро пожаловать в магазин " + nameStore + "!      "+"Сегодня "+time);
        System.out.println("\t\t\tМы представляем ноутбуки на любой вкус!");
        System.out.println("\t\t\t\tСейчас в наличие " + count + " ноутбуков!");
        System.out.println("\t\t(Количество ноутбуков генерируется от 15 до 40)\n");

        fillSet(laptops, infoBase, count);
        showCollection(laptops, infoBase, nameStore);
    }

    static void showCollection(Set<Config> laptopCollection, Set<Config> infoBase, String nameStore) {
        System.out.println("Распечатать весь список[Печать] ноутбуков или подобрать[Подбор] по параметрам?");
        Scanner scan = new Scanner(System.in);
        String resScan = scan.next();
        if (resScan.equals("Печать")) {
            for (Config it : laptopCollection) {
                System.out.println(it);
            }
            System.out.println("");
            System.out.println("\t\tСписок всех доступных ноутбуков сохранён в файл Laptops_Collection.txt\n");
            showCollection(laptopCollection, infoBase, nameStore);
        } else if (resScan.equals("Подбор")) {
            getLaptop(laptopCollection, infoBase, nameStore);
        } else {
            System.out.println("Введите верное значение!");
            showCollection(laptopCollection, infoBase, nameStore);
        }
    }

    static void laptopFill(Set<Config> laptops) {
        Config laptop = new Config();
        laptop.color = laptop.getColor();
        laptop.brand = laptop.getBrand();
        laptop.inch = laptop.getInch();
        laptop.hdd = laptop.getHdd();
        laptop.ram = laptop.getRam();
        laptop.system = laptop.getSystem();
        laptops.add(laptop);
    }

    static void fillSet(Set<Config> laptops, Set<Config> infoBase, int count) {
        for (int i = 0; i < count; i++) {
            laptopFill(laptops);
        }
        infoBase.addAll(laptops);

        try (FileWriter writer = new FileWriter("Laptops_Collection.txt")) {
            writer.flush();
            for (Config it : infoBase
            ) {
                String text = new String(it.toString().getBytes(), "UTF-8");
                writer.write(text + "\n");

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    static void writeFile(Set<Config> base_collection) {
        try (FileWriter writer = new FileWriter("Found_Model.txt")) {
            for (Config it : base_collection
            ) {
                String text = new String(it.toString().getBytes(), "UTF-8");
                writer.write(text + "\n");

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static void getLaptop(Set<Config> laptops, Set<Config> infoBase, String nameStore) {
        Set<Config> newSet = new HashSet<>();
        System.out.println("[1] Производитель; [2] Диагональ; [3] HDD; [4] RAM; [5] Операционная система; [6] Цвет; ");
        System.out.print("Выберите нужный параметр введя цифру: ");
        Scanner insert = new Scanner(System.in);
        int getScan = insert.nextInt();
        if (getScan == 1) {
            System.out.println("Поиск по производителям: [Asus],[Acer],[Lenovo],[HP],[Huawei]");
            System.out.print("Введите название предпочтительного бренда из списка: ");
            String name = insert.next();
            int  checkCount = 0;
            for (Config it : laptops) {
                if (name.equals(it.brand)) {
                    System.out.println(it);
                    newSet.add(it);
                    writeFile(newSet);
                    checkCount++;
                }
            }
            if(checkCount<1){
                System.out.println("Данной модели нет в наличие!");
                getLaptop(laptops, infoBase, nameStore);
            }
            else {
                stepNext(insert, newSet, laptops, infoBase, nameStore);
            }

        } else if (getScan == 2) {
            System.out.println("Поиск по диагонали: [14.1],[15.6],[16.0],[17.3]");
            System.out.print("Введите минимальную диагональ(можно использовать запятую): ");
            double mark = insert.nextDouble();
            for (Config it : laptops) {
                if (mark <= it.inch) {
                    System.out.println(it);
                    newSet.add(it);
                    writeFile(newSet);
                }
            }
            stepNext(insert, newSet, laptops, infoBase, nameStore);
        } else if (getScan == 3) {
            System.out.print("Укажите минимальный объём HDD(2048 max): ");
            int scan = insert.nextInt();
            for (Config it : laptops) {
                if (scan <= it.hdd) {
                    System.out.println(it);
                    newSet.add(it);
                    writeFile(newSet);
                }
            }
            stepNext(insert, newSet, laptops, infoBase, nameStore);
        } else if (getScan == 4) {
            System.out.print("Введите минимальный объём RAM(32 max): ");
            int scan = insert.nextInt();
            for (Config it : laptops) {
                if (scan <= it.ram) {
                    System.out.println(it);
                    newSet.add(it);
                    writeFile(newSet);
                }
            }
            stepNext(insert, newSet, laptops, infoBase, nameStore);
        } else if (getScan == 5) {
            System.out.println("Поиск по системе: [Windows],[Linux],[Android],[noOS]");
            System.out.print("Введите название предпочтительной системы из списка: ");
            String system = insert.next();
            int  checkCount = 0;
            for (Config it : laptops) {
                if (system.equals(it.system)) {
                    System.out.println(it);
                    newSet.add(it);
                    writeFile(newSet);
                    checkCount++;
                }
            }
            if(checkCount<1){
                System.out.println("Данной системы нет в наличие!");
                getLaptop(laptops, infoBase, nameStore);
            }
            else {
                stepNext(insert, newSet, laptops, infoBase, nameStore);
            }
        } else if (getScan == 6) {
            System.out.println("Поиск по цвету: [Белый],[Чёрный],[Серебристый],[Золотой]");
            System.out.print("Введите предпочтительный цвет из списка: ");
            String color = insert.next();
            int checkCount = 0;
            for (Config it : laptops) {
                if (color.equals(it.color)) {
                    System.out.println(it);
                    newSet.add(it);
                    writeFile(newSet);
                    checkCount++;
                }
            }
            if(checkCount<1){
                System.out.println("Данного цвета нет в наличие!");
                getLaptop(laptops, infoBase, nameStore);
            }
            else {
                stepNext(insert, newSet, laptops, infoBase, nameStore);
            }
        } else {
            System.out.println("Вы ввели неверное значение!");
            getLaptop(laptops, infoBase, nameStore);
        }
        insert.close();
    }

    static void stepNext(Scanner insert, Set<Config> newSet, Set<Config> laptops, Set<Config> infoBase, String nameStore) {
        System.out.println("Запустить заново[Заново] или фильтровать текущий список[Дальше] или [Стоп] для остановки");
        String chat = insert.next();
        if (chat.equals("Дальше")) {
            getLaptop(newSet, laptops, nameStore);
        } else if (chat.equals("Заново")) {
            showCollection(infoBase, infoBase, nameStore);
        } else if (chat.equals("Стоп")) {
            System.out.println("Ноутбуки полученные по вашим критериям сохранены в файл Found_Model.txt");
            System.out.println("Количество ноутбуков по вашему запросу: " + newSet.size() + "шт.\n");
            System.out.println("\t\t\tВсего хорошего! Команда " + nameStore + "!");
            return;
        }
        else {
            System.out.println("Введите корректную фразу!");
            stepNext(insert,newSet,laptops,infoBase,nameStore);
        }
        return;
    }
}