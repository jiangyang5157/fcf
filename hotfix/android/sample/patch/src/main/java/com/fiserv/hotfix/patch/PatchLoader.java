package com.fiserv.hotfix.patch;

import android.app.Application;
import android.util.Log;

import java.lang.reflect.Field;

import dalvik.system.DexClassLoader;

public class PatchLoader {

    private static final String TAG = PatchLoader.class.getSimpleName();

    private Application mApplication;

    private String mPatchClassRepoName;

    private String mClassNameIdentifier;

    private String mFieldIdentifier;

    private static PatchLoader mInstance;

    public static synchronized PatchLoader getInstance() {
        if (mInstance == null) {
            mInstance = new PatchLoader();
        }
        return mInstance;
    }

    private PatchLoader() {
    }

    public void initialize(Application application,
                           String patchClassRepoName,
                           String classNameIdentifier,
                           String fieldIdentifier) {
        mApplication = application;
        mPatchClassRepoName = patchClassRepoName;
        mClassNameIdentifier = classNameIdentifier;
        mFieldIdentifier = fieldIdentifier;
    }

    private boolean isInitialized() {
        return !(mApplication == null || mPatchClassRepoName == null || mClassNameIdentifier == null || mFieldIdentifier == null);
    }

    public void load(String patchPath) throws RuntimeException {
        if (!isInitialized()) {
            throw new RuntimeException(TAG + ": Please initialize PatchLoader from Application by PatchLoader#initialize(application, patchClassRepoName, classNameIdentifier, fieldIdentifier)");
        }

        try {
            ClassLoader classLoader = getClass().getClassLoader();
            DexClassLoader dexClassLoader = new DexClassLoader(patchPath, mApplication.getCacheDir().getPath(), null, classLoader);

            Class<?> patchClassRepoObject = Class.forName(mPatchClassRepoName, true, dexClassLoader);
            PatchClassRepo patchClassRepo = (PatchClassRepo) patchClassRepoObject.newInstance();

            for (String className : patchClassRepo.getNames()) {
                int indexOfPatchIdentifier = className.indexOf(mClassNameIdentifier);
                if (indexOfPatchIdentifier == -1) {
                    Log.e(TAG, "Couldn't find patch class name identifier <" + mClassNameIdentifier + "> from class name: " + className);
                    continue;
                }

                Class<?> classObject = dexClassLoader.loadClass(className);
                Object classInstance = classObject.newInstance();
                String originalClassName = className.substring(0, indexOfPatchIdentifier);
                Class<?> originalClassObject = classLoader.loadClass(originalClassName);
                Field fieldOfSaviorIdentifier = originalClassObject.getDeclaredField(mFieldIdentifier);
                fieldOfSaviorIdentifier.setAccessible(true);
                fieldOfSaviorIdentifier.set(null, classInstance);
            }
        } catch (ClassNotFoundException | InstantiationException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}