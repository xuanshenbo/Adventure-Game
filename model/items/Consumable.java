package model.items;

import javax.xml.bind.annotation.XmlSeeAlso;

@XmlSeeAlso({ Cupcake.class, Pumpkin.class })
public interface Consumable {

}
