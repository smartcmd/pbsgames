package top.mcpbs.games.util;

import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementButtonImageData;

public class SElementButton extends ElementButton {

    public Object s;

    public SElementButton(String text, Object s){
        super(text);
        this.s = s;
    }
    public SElementButton(String text, ElementButtonImageData image, Object s) {
        super(text, image);
        this.s = s;
    }
}
