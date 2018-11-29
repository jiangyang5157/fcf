package com.fiserv.plugin

import org.gradle.api.Project

import javassist.ClassPool
import javassist.CtClass
import javassist.CtField
import javassist.CtMethod
import javassist.Modifier

public class MyInjection {

    private static final String IDENTIFIER_CLASS_NAME = "\$Patch"
    private static final String IDENTIFIER_FIELD = "\$Savior"
    private static final String APP_PACKAGE_NAME = "com.fiserv.hotfix.sample"
    private static final String PATCH_PACKAGE_NAME = "com.fiserv.hotfix.patch"
    private static final String SAVIOR_CLASS_NAME = PATCH_PACKAGE_NAME + ".Savior"

    private final static ClassPool mClassPool = ClassPool.getDefault()

    public static void inject(String path, Project project) {
        println("#### inject: " + path)
        mClassPool.appendClassPath(path)
        mClassPool.appendClassPath(project.android.bootClasspath[0].toString())

//        int rootIndex = path.indexOf("/app/build/intermediates/")
//        String saviorPath = path.substring(0, rootIndex) + "/savior/build/intermediates/classes/release"
//        mClassPool.appendClassPath(saviorPath)
//        mClassPool.insertClassPath("/Users/wangxiandeng/Library/Android/sdk/platforms/android-24/android.jar")

        File dir = new File(path)
        if (dir.isDirectory()) {
            dir.eachFileRecurse { File file ->
                println "#### file: " + file
//                if (file.getName() == "MainActivity.class") {
//                    CtClass ctClass = pool.getCtClass(APP_PACKAGE_NAME + ".MainActivity")
//                    println("ctClass = " + ctClass)
//                    if (ctClass.isFrozen()) {
//                        ctClass.defrost()
//                    }
//                    CtMethod ctMethod = ctClass.getDeclaredMethod("onCreate")
//                    ctMethod.insertBefore("// asd")
//                    ctClass.writeFile(path)
//                    ctClass.detach()
//                }
            }
        }






//                String filePath = file.absolutePath
//                if (filePath.endsWith(".class")
//                        && !filePath.contains('R$')
//                        && !filePath.contains('R.class')
//                        && !filePath.contains("BuildConfig.class")
//                        && !filePath.contains("PatchClassRepo.class")
//                        && !filePath.contains(IDENTIFIER_CLASS_NAME + ".class")) {
//                    int index = filePath.indexOf(APP_PACKAGE_NAME)
//                    boolean isMyPackage = index != -1
//                    if (isMyPackage) {
//                        int end = filePath.length() - 6 // .class = 6
//                        String className = filePath.substring(index, end)
//                                .replace('\\', '.')
//                                .replace('/', '.')
//
//                        CtClass cls = mClassPool.getCtClass(className)
//                        if (cls.isFrozen()) {
//                            cls.defrost()
//                        }
//
//                        mClassPool.importPackage(PATCH_PACKAGE_NAME)
//                        CtField saviorField = new CtField(mClassPool.get(SAVIOR_CLASS_NAME), IDENTIFIER_FIELD, cls)
//
//                        saviorField.setModifiers(Modifier.STATIC)
//                        cls.addField(saviorField)
//
//                        CtMethod[] methods = cls.getDeclaredMethods()
//                        for (CtMethod method : methods) {
//                            StringBuilder injectStr = new StringBuilder()
//                            injectStr.append("if(\$savior!=null){\n")
//                            String javaThis = "null,"
//                            if (!Modifier.isStatic(method.getModifiers())) {
//                                javaThis = "this,"
//                            }
//                            String runStr = "\$savior.dispatchMethod(" + javaThis + "\"" + method.getName() + "." + method.getSignature() + "\" ,\$args)"
//                            injectStr.append(addReturnStr(method, runStr))
//                            injectStr.append("}")
//                            print("插入了：" + injectStr.toString() + "语句")
//                            method.insertBefore(injectStr.toString())
//                        }
//                        cls.writeFile(path)
//                        cls.detach()
//                    }
//                }
//            }
//        }
    }

    public static String getReturnType(String methodSign) {
        String type = ""
        int index = methodSign.indexOf(")L")
        String jType = methodSign.substring(index + 2, methodSign.length() - 1)
        type = jType.replace("/", ".")
        return type;
    }

    public static String addReturnStr(CtMethod method, String runStr) {
        String returnStr = ""
        String typeStr = ""
        switch (method.getReturnType()) {
            case CtClass.voidType:
                return runStr + ";\n return;"
                break
            case CtClass.booleanType:
                returnStr = "return ((Boolean)"
                typeStr = ".booleanValue()"
                break
            case CtClass.byteType:
                returnStr = "return ((byte)"
                typeStr = ".byteValue()"
                break
            case CtClass.charType:
                returnStr = "return ((char)"
                typeStr = ".charValue()"
                break
            case CtClass.doubleType:
                returnStr = "return ((Number)"
                typeStr = ".doubleValue()"
                break
            case CtClass.floatType:
                returnStr = "return ((Number)"
                typeStr = ".floatValue()"
                break
            case CtClass.intType:
                returnStr = "return ((Number)"
                typeStr = ".intValue()"
                break
            case CtClass.longType:
                returnStr = "return ((Number)"
                typeStr = ".longValue()"
                break
            case CtClass.shortType:
                returnStr = "return ((Number)"
                typeStr = ".shortValue()"
                break
            default:
                returnStr = "return((" + getReturnType(method.getSignature()) + ")"
                break
        }
        return returnStr + "(" + runStr + "))" + typeStr + ";\n"
    }
}
