package com.fiserv.plugin

import com.android.annotations.NonNull
import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.FileUtils
import org.gradle.api.Project

public class ClassInjectionTransform extends Transform {

    private static final String TAG = ClassInjectionTransform.class.simpleName
    private static final String NAME = "preDexClassInjection"

    Project project

    public ClassInjectionTransform(Project project) {
        this.project = project
    }

    // task name
    @Override
    String getName() {
        return NAME
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(@NonNull TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation)
//        transformInvocation.inputs.each { TransformInput input ->
//            //遍历文件夹
//            input.directoryInputs.each { DirectoryInput directoryInput ->
//                //注入代码
//                InjectClass.inject(directoryInput.file.absolutePath, project, injectCode)
//                // 获取output目录
//                def dest = transformInvocation.outputProvider.getContentLocation(directoryInput.name, directoryInput.contentTypes, directoryInput.scopes, Format.DIRECTORY)
//                // 将input的目录复制到output指定目录
//                FileUtils.copyDirectory(directoryInput.file, dest)
//            }
//            //遍历jar文件 对jar不操作，但是要输出到out路径
//            input.jarInputs.each { JarInput jarInput ->
//                // 重命名输出文件（同名文件copyFile会冲突）
//                def jarName = jarInput.name
//                println("jar = " + jarInput.file.getAbsolutePath())
//                def md5Name = DigestUtils.md5Hex(jarInput.file.getAbsolutePath())
//                if (jarName.endsWith(".jar")) {
//                    jarName = jarName.substring(0, jarName.length() - 4)
//                }
//                def dest = transformInvocation.outputProvider.getContentLocation(jarName + md5Name, jarInput.contentTypes, jarInput.scopes, Format.JAR)
//                FileUtils.copyFile(jarInput.file, dest)
//            }
//        }



//        print("Wangxiandeng is so cool")
//        // Transform的inputs有两种类型，一种是目录，一种是jar包，要分开遍历
//        transformInvocation.inputs.each { TransformInput input ->
//            //对类型为“文件夹”的input进行遍历
//            input.directoryInputs.each { DirectoryInput directoryInput ->
//                //文件夹里面包含的是我们手写的类以及R.class、BuildConfig.class以及R$XXX.class等
//                MyInject.injectDir(directoryInput.file.absolutePath, "com/wangxiandeng/saviortest")
//                // 获取output目录
//                def dest = transformInvocation.outputProvider.getContentLocation(directoryInput.name,
//                        directoryInput.contentTypes, directoryInput.scopes,
//                        Format.DIRECTORY)
//
//                // 将input的目录复制到output指定目录
//                FileUtils.copyDirectory(directoryInput.file, dest)
//            }
//            //对类型为jar文件的input进行遍历
//            input.jarInputs.each { JarInput jarInput ->
//
//                //jar文件一般是第三方依赖库jar文件
//
//                // 重命名输出文件（同目录copyFile会冲突）
//                def jarName = jarInput.name
//                def md5Name = DigestUtils.md5Hex(jarInput.file.getAbsolutePath())
//                if (jarName.endsWith(".jar")) {
//                    jarName = jarName.substring(0, jarName.length() - 4)
//                }
//                //生成输出路径
//                def dest = transformInvocation.outputProvider.getContentLocation(jarName + md5Name,
//                        jarInput.contentTypes, jarInput.scopes, Format.JAR)
//                //将输入内容复制到输出
//                FileUtils.copyFile(jarInput.file, dest)
//            }
//        }
    }
}