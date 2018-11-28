package com.fiserv.fcf.hotfix.buildsrc

import com.android.annotations.NonNull
import com.android.build.api.transform.DirectoryInput
import com.android.build.api.transform.JarInput
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformInput
import com.android.build.api.transform.TransformInvocation
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.FileUtils
import org.gradle.api.Project

public class MyTransform extends Transform {

    private Project project

    public MyTransform(Project project) {
        this.project = project
    }

    @Override
    public String getName() {
        return MyTransform.class.simpleName
    }

    @Override
    public Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    public Set<QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    public boolean isIncremental() {
        return false
    }

    @Override
    public void transform(@NonNull TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        print("com.fiserv.fcf.hotfix.buildsrc.MyTransform#transform")

        transformInvocation.inputs.each { TransformInput input ->

            // classes, R.class, BuildConfig.class, R$xxx.class, ...
            input.directoryInputs.each { DirectoryInput directoryInput ->
                MyInject.injectDir(directoryInput.file.absolutePath, "com.fiserv.fcf.hotfix")
                def dest = transformInvocation.outputProvider.getContentLocation(
                        directoryInput.name,
                        directoryInput.contentTypes,
                        directoryInput.scopes,
                        Format.DIRECTORY)
                FileUtils.copyDirectory(directoryInput.file, dest)
            }

            // 3rd party jar files
            input.jarInputs.each { JarInput jarInput ->
                def jarName = jarInput.name
                def md5Name = DigestUtils.md5Hex(jarInput.file.getAbsolutePath())
                if (jarName.endsWith(".jar")) {
                    jarName = jarName.substring(0, jarName.length() - 4)
                }
                def dest = transformInvocation.outputProvider.getContentLocation(
                        jarName + md5Name,
                        jarInput.contentTypes,
                        jarInput.scopes,
                        Format.JAR)
                FileUtils.copyFile(jarInput.file, dest)
            }
        }
    }

}