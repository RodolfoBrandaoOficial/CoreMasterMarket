package com.rodolfobrandao.coremastermarket.tools.swagger;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SwaggerAspectTest {
//package com.rodolfobrandao.coremastermarket.tools.swagger;
//
//import io.swagger.v3.oas.annotations.Operation;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.springframework.core.annotation.AnnotationUtils;
//import org.springframework.stereotype.Component;
//
//import java.lang.reflect.Field;
//import java.lang.reflect.Method;
//import java.util.Arrays;
//
///**
// * Aspecto para processar automaticamente as anotações de operação do Swagger
// * para métodos de controladores anotados com {@link DefaultOperation}.
// */
//@Aspect
//@Component
//public class SwaggerAspect {
//
//    /**
//     * Intercepta métodos de controladores para processar e definir valores padrão
//     * das anotações de operação do Swagger.
//     *
//     * @param joinPoint o ponto de junção representando a execução do método
//     * @throws Throwable se ocorrer algum erro durante a reflexão
//     */
//    @Before("execution(public * com.rodolfobrandao.coremastermarket.controllers..*(..))")
//    public void processDefaultOperation(JoinPoint joinPoint) throws Throwable {
//        Method method = getMethodFromJoinPoint(joinPoint);
//        DefaultOperation defaultOperation = AnnotationUtils.findAnnotation(method, DefaultOperation.class);
//
//        if (defaultOperation != null) {
//            String summary = defaultOperation.summary().isEmpty() ? generateSummaryFromMethod(method) : defaultOperation.summary();
//            String description = defaultOperation.description().isEmpty() ? generateDescriptionFromMethod(method) : defaultOperation.description();
//            String[] tags = defaultOperation.tags().length == 0 ? new String[]{generateTagFromClassName(method.getDeclaringClass())} : defaultOperation.tags();
//
//            Operation operation = AnnotationUtils.getAnnotation(method, Operation.class);
//            if (operation != null) {
//                updateOperationField(operation, "summary", summary);
//                updateOperationField(operation, "description", description);
//                updateOperationField(operation, "tags", Arrays.toString(tags));
//            }
//        }
//    }
//
//    /**
//     * Obtém o método sendo executado a partir do ponto de junção.
//     *
//     * @param joinPoint o ponto de junção representando a execução do método
//     * @return o método sendo executado
//     * @throws NoSuchMethodException se o método não puder ser encontrado
//     */
//    private Method getMethodFromJoinPoint(JoinPoint joinPoint) throws NoSuchMethodException {
//        String methodName = joinPoint.getSignature().getName();
//        Class<?> targetClass = joinPoint.getTarget().getClass();
//        return Arrays.stream(targetClass.getMethods())
//                .filter(m -> m.getName().equals(methodName))
//                .findFirst()
//                .orElseThrow(() -> new NoSuchMethodException("Método não encontrado: " + methodName));
//    }
//
//    /**
//     * Gera uma string de resumo com base no nome da classe que declara o método.
//     *
//     * @param method o método sendo processado
//     * @return a string de resumo gerada
//     */
//    private String generateSummaryFromMethod(Method method) {
//        String className = getSimpleClassName(method.getDeclaringClass());
//        return "Listar " + className + "s";
//    }
//
//    /**
//     * Gera uma string de descrição com base no nome da classe que declara o método.
//     *
//     * @param method o método sendo processado
//     * @return a string de descrição gerada
//     */
//    private String generateDescriptionFromMethod(Method method) {
//        String className = getSimpleClassName(method.getDeclaringClass());
//        return "Lista todas as " + className + "s cadastradas";
//    }
//
//    /**
//     * Gera uma string de tag com base no nome da classe.
//     *
//     * @param declaringClass a classe sendo processada
//     * @return a string de tag gerada
//     */
//    private String generateTagFromClassName(Class<?> declaringClass) {
//        return getSimpleClassName(declaringClass);
//    }
//
//    /**
//     * Simplifica o nome da classe removendo "Controller" e capitalizando apropriadamente.
//     *
//     * @param clazz a classe para simplificar o nome
//     * @return o nome simplificado da classe
//     */
//    private String getSimpleClassName(Class<?> clazz) {
//        String className = clazz.getSimpleName().replace("Controller", "");
//        return className.substring(0, 1).toUpperCase() + className.substring(1).toLowerCase();
//    }
//
//    /**
//     * Atualiza o campo especificado de uma instância da anotação {@link Operation}.
//     *
//     * @param operation a instância da anotação Operation
//     * @param fieldName o nome do campo a ser atualizado
//     * @param value o novo valor a ser definido
//     */
//    public void updateOperationField(Object operation, String fieldName, String value) {
//        try {
//            Field field = operation.getClass().getDeclaredField(fieldName);
//            field.setAccessible(true);
//            field.set(operation, value);
//        } catch (NoSuchFieldException e) {
//            // Handle the case where the field does not exist
//            System.err.println("Field " + fieldName + " does not exist on " + operation.getClass().getName());
//        } catch (IllegalAccessException e) {
//            // Handle the case where the field cannot be accessed
//            e.printStackTrace();
//        }
//    }
//}
    @Test
    void processDefaultOperation() {
    }

    @Test
    void updateOperationField() {
    }
}
