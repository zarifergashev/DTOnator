package com.nvinayshetty.DTOnator.DtoCreators;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;
import com.nvinayshetty.DTOnator.ClassCreator.ClassCreationFactory;
import com.nvinayshetty.DTOnator.ClassCreator.ClassType;
import com.nvinayshetty.DTOnator.FeedParser.JsonDtoGenerator;
import com.nvinayshetty.DTOnator.FieldCreator.AccessModifier;
import com.nvinayshetty.DTOnator.FieldCreator.FieldCreationFactory;
import org.json.JSONObject;

import java.util.EnumSet;

/**
 * Created by vinay on 17/5/15.
 */
public class DtoGenerationFactory {
    public static WriteCommandAction getDtoGeneratorFor(FeedType type, ClassType classType, FieldType fieldType, EnumSet<FieldEncapsulationOptions> fieldEncapsulationOptions, Project project, PsiFile psiFile, JSONObject validFeed, PsiClass psiClass) {
        AccessModifier accessModifier = null;
        if (fieldEncapsulationOptions.contains(FieldEncapsulationOptions.PROVIDE_PRIVATE_FIELD))
            accessModifier = AccessModifier.PRIVATE;
        else
            accessModifier = AccessModifier.PUBLIC;

        switch (type) {
            case JSON:
                DtoCreationOptionsFacade dtoCreationOptionsFacade = new DtoCreationOptionsFacade(FieldCreationFactory.getFieldCreatorFor(fieldType), ClassCreationFactory.getFileCreatorFor(classType, psiClass), accessModifier, fieldEncapsulationOptions);
                return JsonDtoGenerator.getJsonDtoBuilder().setClassUnderCaret(psiClass).setDtoCreationOptionsFacade(dtoCreationOptionsFacade).setJson(validFeed).createJsonDtoGenerator();
        }
        return null;
    }
}