package com.example.microservices20;

import jdk.nashorn.api.scripting.ClassFilter;
import jdk.nashorn.api.scripting.NashornScriptEngineFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.script.ScriptEngine;

@Configuration
public class BeanFactory {

    @Bean
    public ScriptEngine createJavaScriptEngine(){
        NashornScriptEngineFactory factory = new NashornScriptEngineFactory();
        return factory.getScriptEngine(new NoJavaFilter());
    }

    private class NoJavaFilter implements ClassFilter {
        @Override
        public boolean exposeToScripts(String className) {
            return false;
        }
    }

}
