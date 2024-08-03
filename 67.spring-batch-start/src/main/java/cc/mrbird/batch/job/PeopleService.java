package cc.mrbird.batch.job;

import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class PeopleService {
    public People upperCase(People people) {
        People p = new People();
        p.setName(people.getName().toUpperCase(Locale.ROOT));
        p.setAdress(people.getAdress().toUpperCase(Locale.ROOT));
        p.setAge(people.getAge());
        p.setIdCard(people.getIdCard());
        System.out.println("p：" + p);
        return p;
    }
}
