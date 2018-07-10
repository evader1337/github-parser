package com.dk.faculty.graphqlparser.beans;

import com.dk.faculty.graphqlparser.entities.*;

import javax.enterprise.context.RequestScoped;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RequestScoped
public class Parser {
    private String parseRootName(Map data, String query) {
        try {
            return (String)((Map)data.get(query)).get("name");
        } catch (Exception e) {
            return null;
        }
    }

    public SchemaObject parseSchema(Map data) {
        String queryType = parseRootName(data, "queryType");
        String mutationType = parseRootName(data, "mutationType");
        String subscriptionType = parseRootName(data, "subscriptionType");
        SchemaObject schemaObject = new SchemaObject();
        List<CustomType> customTypes = new ArrayList<>();
        ((ArrayList)data.get("types")).stream().forEach(entry -> {
            Map types = (Map) entry;
            if(types.get("name").equals(queryType)) {
                try {
                    ArrayList fields = (ArrayList) types.get("fields");
                    schemaObject.setQueryOperations(processFields(fields));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } else if(types.get("name").equals(mutationType)) {
                try {
                    ArrayList fields = (ArrayList) types.get("fields");
                    schemaObject.setMutationOperations(processFields(fields));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } else if(types.get("name").equals(subscriptionType)) {
                try {
                    ArrayList fields = (ArrayList) types.get("fields");
                    schemaObject.setSubscriptionOperations(processFields(fields));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } else {
                try {
                    CustomType type = processCustomType(types);
                    if(type != null) {
                        customTypes.add(type);
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });
        schemaObject.setCustomTypes(customTypes);
        return schemaObject;
    }

    public List<Operation> processFields(ArrayList fields) {
        List<Operation> operations = new ArrayList<>();
        fields.stream().forEach(fieldObject -> {
            Map field = (Map) fieldObject;
            Operation o = new Operation();
            o.setName((String) field.get("name"));
            o.setDescription((String) field.get("description"));
            o.setDeprecated((Boolean) field.get("isDeprecated"));
            o.setDeprecationReason((String) field.get("deprecationReason"));
            try {
                o.setArguments(processArguments((ArrayList)field.get("args")));
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
            try {
                o.setType(processType((Map)field.get("type")));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            operations.add(o);
        });
        return operations;
    }

    public List<Argument> processArguments(ArrayList argumentsObject) {
        List<Argument> arguments = new ArrayList<>();
        argumentsObject.stream().forEach(argumentObject -> {
            Map argument = (Map) argumentObject;
            Argument arg = new Argument();
            arg.setName((String) argument.get("name"));
            arg.setDecription((String) argument.get("description"));
            arg.setDefaultValue((String) argument.get("defaultValue"));
            try {
                arg.setType(processType((Map)argument.get("type")));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            arguments.add(arg);
        });
        return arguments;
    }

    public String processType(Map typeMap) {
        if(typeMap.get("ofType") != null) {
            String type = processType((Map)typeMap.get("ofType"));
            if(typeMap.get("kind").equals("LIST")) {
                return "[" + type + "]";
            } else if(typeMap.get("kind").equals("NON_NULL")) {
                return type + "!";
            } else {
                return typeMap.get("kind") + " " + type;
            }
        } else {
            return (String)typeMap.get("name");
        }
    }

    public CustomType processCustomType(Map type) {
        CustomType customType = new CustomType();
        String[] invalidNames = {"String", "Int", "Boolean", "__Schema", "__Type", "__TypeKind", "__Field", "__InputValue", "__EnumValue", "__Directive", "__DirectiveLocation"};
        String name = (String) type.get("name");
        if(!Arrays.asList(invalidNames).contains(name)) {
            customType.setName(name);
            customType.setDescription((String) type.get("description"));
            customType.setKind((String) type.get("kind"));
            if(type.get("fields") != null) {
                customType.setFields(processFields((ArrayList)type.get("fields")));
            }
            if(type.get("enumValues") != null) {
                customType.setEnumValues(processEnumValues((ArrayList)type.get("enumValues")));
            }
            if(type.get("interfaces") != null) {
                List<String> interfaces = new ArrayList<>();
                ((ArrayList)type.get("interfaces")).stream().forEach(intf -> {
                    interfaces.add(processType((Map) intf));
                });
                customType.setInterfaces(interfaces);
            }
            if(type.get("possibleTypes") != null) {
                List<String> possibleTypes = new ArrayList<>();
                ((ArrayList)type.get("possibleTypes")).stream().forEach(intf -> {
                    possibleTypes.add(processType((Map) intf));
                });
                customType.setPossibleTypes(possibleTypes);
            }
            if(type.get("inputFields") != null) {
                customType.setInputFields(processArguments((ArrayList) type.get("inputFields")));
            }
            return customType;
        } else {
            return null;
        }
    }

    public List<EnumValue> processEnumValues(ArrayList values) {
        List<EnumValue> enumValues = new ArrayList<>();
        values.stream().forEach(item -> {
            Map enumValueMap = (Map) item;
            EnumValue enumValue = new EnumValue();
            enumValue.setName((String) enumValueMap.get("name"));
            enumValue.setDescription((String) enumValueMap.get("description"));
            enumValue.setDeprecated((Boolean) enumValueMap.get("isDeprecated"));
            enumValue.setDeprecationReason((String) enumValueMap.get("deprecationReason"));
            enumValues.add(enumValue);
        });
        return enumValues;
    }
}
