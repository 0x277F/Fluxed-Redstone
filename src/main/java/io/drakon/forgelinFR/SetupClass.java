package io.drakon.forgelinFR;

import net.minecraftforge.fml.relauncher.IFMLCallHook;

import java.util.Map;

public class SetupClass implements IFMLCallHook {

    @Override
    public void injectData(Map<String, Object> data) {
        ClassLoader classLoader = (ClassLoader) data.get("classLoader");
        try {
            classLoader.loadClass("io.drakon.forgelinFR.KotlinAdapter");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Void call() throws Exception {
        return null;
    }
}
