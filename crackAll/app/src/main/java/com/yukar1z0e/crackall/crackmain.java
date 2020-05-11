package com.yukar0z0e.crackall;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.ArrayList;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedHelpers.callMethod;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import static de.robv.android.xposed.XposedHelpers.findClass;
import static de.robv.android.xposed.XposedHelpers.findField;

public class crackmain implements IXposedHookLoadPackage {
    private XC_LoadPackage.LoadPackageParam lpparam = null;
    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        lpparam = loadPackageParam;
        if (lpparam.packageName.contains("com.tencent.mm")) {
            findAndHookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    crackWeChat_getPhoneNumber();
                    crackWeChat_getInfo();
                    crackWeChat_isExist();
                    crackWeChat_backContactInfoUI();
                }
            });
        }else if(lpparam.packageName.contains("com.whatsapp")){
            findAndHookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    crackWhatsApp_crackRootAndROMCheck();
                    crackWhatsApp_getInfo();
                }
            });
        }else if(lpparam.packageName.contains("org.telegram")){
            findAndHookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    crackTelegram_getInfo();
                }
            });
        }else if(lpparam.packageName.contains("com.tencent")){
            findAndHookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    crackQQ_getPeerInfo();
                    crackQQ_getPeerAvatar();
                }
            });
        }else if(lpparam.packageName.contains("org.potato")){
            findAndHookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    crackPotato_getInfo();
                }
            });
        }
    }
    /*
     * WeChat
     * */
    public void crackWeChat_killXposedTest() {
        findAndHookMethod("com.tencent.mm.app.t", lpparam.classLoader, "a", StackTraceElement[].class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                if ((Boolean) param.getResult()) {
                    param.setResult(false);
                }
            }
        });
    }

    public void crackWeChat_getPhoneNumber() {
        crackWeChat_killXposedTest();
        final Class<?> FTSAddFriendUIClass = findClass("com.tencent.mm.plugin.fts.ui.FTSAddFriendUI", lpparam.classLoader);
        findAndHookMethod(FTSAddFriendUIClass, "Mf", String.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                Log.d("crackWeChat", "PhoneNumber: " + param.args[0]);
            }
        });
    }

    public void crackWeChat_getInfo() {
        crackWeChat_killXposedTest();
        final Class<?> aoClass = findClass("com.tencent.mm.g.c.ao", lpparam.classLoader);
        final Class<?> ContactInfoUIClass = findClass("com.tencent.mm.plugin.profile.ui.ContactInfoUI", lpparam.classLoader);
        findAndHookMethod(ContactInfoUIClass, "onBackPressed", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(final MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                /* *
                 * Username（微信唯一值）
                 * Alias: wxid/Personal Id
                 * EncryptUsername: encrypt(Username)
                 * pyInitial: decrypt(wxid)
                 * dhK、dhL： Province、City
                 * Sex： 0:NULL 0：MALE 0：FEMALE
                 * */
                Field dUUField = findField(param.thisObject.getClass(), "dUU");
                Object dUUObj = dUUField.get(param.thisObject);
                Field field_username = findField(aoClass, "field_username");
                Field field_alias = findField(aoClass, "field_alias");
                Field field_encryptUsername = findField(aoClass, "field_encryptUsername");
                Field field_pyInitial = findField(aoClass, "field_pyInitial");
                Field field_nickname = findField(aoClass, "field_nickname");
                Field field_province = findField(aoClass, "dhK");
                Field field_city = findField(aoClass, "dhL");
                Field field_signature = findField(aoClass, "signature");
                Field field_sex = findField(aoClass, "sex");
                Log.d("crackWeChat",
                        "Username: " + field_username.get(dUUObj) +
                                ";Alias: " + field_alias.get(dUUObj) +
                                ";EncryptUsername: " + field_encryptUsername.get(dUUObj) +
                                ";PyInitial: " + field_pyInitial.get(dUUObj) +
                                ";Nickname: " + field_nickname.get(dUUObj) +
                                ";Province: " + field_province.get(dUUObj) +
                                ";City: " + field_city.get(dUUObj) +
                                ";Signature: " + field_signature.get(dUUObj) +
                                ";Sex: " + field_sex.get(dUUObj) +
                                ";END");
            }
        });
    }

    public void crackWeChat_isExist() {
        crackWeChat_killXposedTest();
        final Class<?> FTSAddFriendUI$0Class = findClass("com.tencent.mm.plugin.fts.ui.FTSAddFriendUI$0", lpparam.classLoader);
        final Class<?> mClass = findClass("com.tencent.mm.ah.m", lpparam.classLoader);
        findAndHookMethod(FTSAddFriendUI$0Class, "onSceneEnd", int.class, int.class, String.class, mClass, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                Log.d("crackWeChat", param.args[0].toString());
            }
        });
    }

    public void crackWeChat_backContactInfoUI() {
        final Class<?> ContactInfoUIClass = findClass("com.tencent.mm.plugin.profile.ui.ContactInfoUI", lpparam.classLoader);
        findAndHookMethod(ContactInfoUIClass, "onCreate", Bundle.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(final MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        callMethod(param.thisObject, "onBackPressed");
                    }
                });
            }
        });
    }

    /*
     * WhatsApp
     * */
    public void crackWhatsApp_crackRootAndROMCheck() {
        final Class<?> aClass = findClass("d.g.fa.a", lpparam.classLoader);
        final Class<?> fClass = findClass("d.g.t.f", lpparam.classLoader);
        final Class<?> mClass = findClass("d.g.t.m", lpparam.classLoader);
        findAndHookMethod(aClass, "f", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                param.setResult(false);
                Field fField = findField(aClass, "f");
                fField.set(param, "miuihhhhhh");
            }
        });

        findAndHookMethod(aClass, "a", fClass, mClass, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                param.setResult(false);
            }
        });
    }

    public void crackWhatsApp_getInfo() {
        final Class<?> ContactInfo$dClass = findClass("com.whatsapp.ContactInfo$d", lpparam.classLoader);
        final Class<?> CdClass = findClass("d.g.x.Cd", lpparam.classLoader);
        findAndHookMethod(ContactInfo$dClass, "doInBackground", Object[].class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                Field ContactInfo$d_b_Field = findField(ContactInfo$dClass, "b");
                Object ContactInfo$d_b_Object = ContactInfo$d_b_Field.get(param.thisObject);
                Field c_Field = findField(CdClass, "c");
                Field d_Field = findField(CdClass, "d");
                Field e_Field = findField(CdClass, "e");
                Field l_Field = findField(CdClass, "l");
                Field m_Field = findField(CdClass, "m");
                Field o_Field = findField(CdClass, "o");
                Field i_Field = findField(CdClass, "i");
                Field n_Field = findField(CdClass, "n");
                Field r_Field = findField(CdClass, "r");
                Field s_Field = findField(CdClass, "s");
                Field t_Field = findField(CdClass, "t");
                Field p_Field = findField(CdClass, "p");
                Field v_Field = findField(CdClass, "v");
                Field w_Field = findField(CdClass, "w");
                Field z_Field = findField(CdClass, "z");
                Log.d("crackWhatsApp",
                        "display name: " + c_Field.get(ContactInfo$d_b_Object)
                                + " ;sort name: " + o_Field.get(ContactInfo$d_b_Object)
                                + " ;Signature: " + p_Field.get(ContactInfo$d_b_Object)
                                + " ;wa name: " + n_Field.get(ContactInfo$d_b_Object)
                                + " ;phone type: " + d_Field.get(ContactInfo$d_b_Object)
                                + " ;phone label: " + e_Field.get(ContactInfo$d_b_Object)
                                + " ;given name: " + l_Field.get(ContactInfo$d_b_Object)
                                + " ;family name: " + m_Field.get(ContactInfo$d_b_Object)
                                + " ;photo: " + Integer.valueOf(i_Field.getInt(ContactInfo$d_b_Object))
                                + " ;nickname: " + r_Field.get(ContactInfo$d_b_Object)
                                + " ;company: " + s_Field.get(ContactInfo$d_b_Object)
                                + " ;title: " + t_Field.get(ContactInfo$d_b_Object)
                                + " ;v: " + v_Field.get(ContactInfo$d_b_Object)
                                + " ;Locale: " + w_Field.get(ContactInfo$d_b_Object)
                                + " ;z: " + z_Field.get(ContactInfo$d_b_Object)
                );
            }
        });
    }

    /*
     * Telegram
     * */
    public void crackTelegram_getInfo() {
        final Class<?> TLRPC$UserClass = findClass("org.telegram.tgnet.TLRPC$User", lpparam.classLoader);
        final Class<?> TLRPC$BotInfoClass = findClass("org.telegram.tgnet.TLRPC$BotInfo", lpparam.classLoader);
        final Class<?> TLRPC$UserFullClass = findClass("org.telegram.tgnet.TLRPC$UserFull", lpparam.classLoader);
        final Class<?> ProfileActivityClass = findClass("org.telegram.ui.ProfileActivity", lpparam.classLoader);
        final Class<?> TLRPC$TL_botCommandClass = findClass("org.telegram.tgnet.TLRPC$TL_botCommand", lpparam.classLoader);
        final Class<?> TLRPC$UserProfilePhotoClass = findClass("org.telegram.tgnet.TLRPC$UserProfilePhoto", lpparam.classLoader);
        final Class<?> TLRPC$UserStatusClass = findClass("org.telegram.tgnet.TLRPC$UserStatus", lpparam.classLoader);
        findAndHookMethod(ProfileActivityClass, "setUserInfo", TLRPC$UserFullClass, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                Field aboutField = findField(TLRPC$UserFullClass, "about");
                Field userField = findField(TLRPC$UserFullClass, "user");
                Object userObject = userField.get(param.args[0]);
                Field first_nameField = findField(TLRPC$UserClass, "first_name");
                Field last_nameField = findField(TLRPC$UserClass, "last_name");
                Field usernameField = findField(TLRPC$UserClass, "username");
                Field idField = findField(TLRPC$UserClass, "id");
                Field phoneField = findField(TLRPC$UserClass, "phone");
                Field lang_codeField = findField(TLRPC$UserClass, "lang_code");
                Field photoField = findField(TLRPC$UserClass, "photo");
                Object photoObject = photoField.get(userObject);
                Field photoidField = findField(TLRPC$UserProfilePhotoClass, "photo_id");
                Field statusField = findField(TLRPC$UserClass, "status");
                Object statusObject = statusField.get(userObject);
                Field expiresField = findField(TLRPC$UserStatusClass, "expires");
                Field restrictedField = findField(TLRPC$UserClass, "restricted");
                Field restriction_reasonField = findField(TLRPC$UserClass, "restriction_reason");
                Field mutual_contactField = findField(TLRPC$UserClass, "mutual_contact");
                Field explicit_contentField = findField(TLRPC$UserClass, "explicit_content");
                Field access_hashField = findField(TLRPC$UserClass, "access_hash");
                if (photoObject != null && statusObject != null) {
                    Log.d("crackTelegram",
                            "phone: " + phoneField.get(userObject)
                                    + ";id: " + idField.get(userObject)
                                    + ";username: " + usernameField.get(userObject)
                                    + ";bio: " + aboutField.get(param.args[0])
                                    + ";access_hash: " + access_hashField.get(userObject)
                                    + ";status: " + statusObject
                                    + ";status expires: " + expiresField.get(statusObject)
                                    + ";first name: " + first_nameField.get(userObject)
                                    + ";last name: " + last_nameField.get(userObject)
                                    + ";photo: " + photoObject
                                    + ";photo_id: " + photoidField.get(photoObject)
                                    + ";lang code: " + lang_codeField.get(userObject)
                                    + ";is restricted: " + restrictedField.get(userObject)
                                    + ";restriction reason: " + restriction_reasonField.get(userObject)
                                    + ";is mutual contact: " + mutual_contactField.get(userObject)
                                    + ";is explicit content: " + explicit_contentField.get(userObject)
                    );
                } else if (photoObject != null) {
                    Log.d("crackTelegram",
                            "phone: " + phoneField.get(userObject)
                                    + ";id: " + idField.get(userObject)
                                    + ";username: " + usernameField.get(userObject)
                                    + ";bio: " + aboutField.get(param.args[0])
                                    + ";access_hash: " + access_hashField.get(userObject)
                                    + ";status: " + statusObject
                                    + ";first name: " + first_nameField.get(userObject)
                                    + ";last name: " + last_nameField.get(userObject)
                                    + ";photo: " + photoObject
                                    + ";photo_id: " + photoidField.get(photoObject)
                                    + ";lang code: " + lang_codeField.get(userObject)
                                    + ";is restricted: " + restrictedField.get(userObject)
                                    + ";restriction reason: " + restriction_reasonField.get(userObject)
                                    + ";is mutual contact: " + mutual_contactField.get(userObject)
                                    + ";is explicit content: " + explicit_contentField.get(userObject)
                    );
                } else if (statusObject != null) {
                    Log.d("crackTelegram",
                            "phone: " + phoneField.get(userObject)
                                    + ";id: " + idField.get(userObject)
                                    + ";username: " + usernameField.get(userObject)
                                    + ";bio: " + aboutField.get(param.args[0])
                                    + ";access_hash: " + access_hashField.get(userObject)
                                    + ";status: " + statusObject
                                    + ";status expires: " + expiresField.get(statusObject)
                                    + ";first name: " + first_nameField.get(userObject)
                                    + ";last name: " + last_nameField.get(userObject)
                                    + ";photo: " + photoObject
                                    + ";lang code: " + lang_codeField.get(userObject)
                                    + ";is restricted: " + restrictedField.get(userObject)
                                    + ";restriction reason: " + restriction_reasonField.get(userObject)
                                    + ";is mutual contact: " + mutual_contactField.get(userObject)
                                    + ";is explicit content: " + explicit_contentField.get(userObject)
                    );
                } else {
                    Log.d("crackTelegram",
                            "phone: " + phoneField.get(userObject)
                                    + ";id: " + idField.get(userObject)
                                    + ";username: " + usernameField.get(userObject)
                                    + ";bio: " + aboutField.get(param.args[0])
                                    + ";access_hash: " + access_hashField.get(userObject)
                                    + ";status: " + statusObject
                                    + ";first name: " + first_nameField.get(userObject)
                                    + ";last name: " + last_nameField.get(userObject)
                                    + ";photo: " + photoObject
                                    + ";lang code: " + lang_codeField.get(userObject)
                                    + ";is restricted: " + restrictedField.get(userObject)
                                    + ";restriction reason: " + restriction_reasonField.get(userObject)
                                    + ";is mutual contact: " + mutual_contactField.get(userObject)
                                    + ";is explicit content: " + explicit_contentField.get(userObject)
                    );
                }
            }
        });

        findAndHookMethod(ProfileActivityClass, "setUserInfo", TLRPC$UserFullClass, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                Field bot_infoField = findField(TLRPC$UserFullClass, "bot_info");
                Object bot_infoObject = bot_infoField.get(param.args[0]);
                if (bot_infoObject != null) {
                    Field descriptionField = findField(TLRPC$BotInfoClass, "description");
                    Field user_idField = findField(TLRPC$BotInfoClass, "user_id");
                    Field commandsField = findField(TLRPC$BotInfoClass, "commands");
                    Log.d("crackTelegram", "bot_user_id: " + user_idField.get(bot_infoObject)
                            + " bot_description: " + descriptionField.get(bot_infoObject)
                            + " bot_commands: " + commandsField.get(bot_infoObject));
                    ArrayList<?> commands = (ArrayList<?>) commandsField.get(bot_infoObject);
                    Field TL_botCommand_commandField = findField(TLRPC$TL_botCommandClass, "command");
                    Field TL_botCommand_descriptionField = findField(TLRPC$TL_botCommandClass, "description");
                    for (int i = 0; i < commands.size(); i++) {
                        Object tmpObj = commands.get(i);
                        Log.d("crackTelegram", " TL_botCommand_command: " + TL_botCommand_commandField.get(tmpObj)
                                + " TL_botCommand_description: " + TL_botCommand_descriptionField.get(tmpObj));
                    }
                }
            }
        });
    }

    /*
     * QQ International Version
     * */
    public void crackQQ_getPeerInfo() {
        final Class<?> ProfileActivity$AllInOneClass = findClass("com.tencent.mobileqq.activity.ProfileActivity$AllInOne", lpparam.classLoader);

        findAndHookMethod(ProfileActivity$AllInOneClass, "a", ProfileActivity$AllInOneClass, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                Log.d("crackQQ", param.args[0].toString());
            }
        });
    }

    public void crackQQ_getPeerAvatar() {
        final Class<?> aClass = findClass("com.tencent.mobileqq.activity.FriendProfileImageAvatar", lpparam.classLoader);
        final Class<?> BaseActivityClass = findClass("com.tencent.mobileqq.app.BaseActivity", lpparam.classLoader);
        final Class<?> ProfileImageInfoClass = findClass("com.tencent.mobileqq.activity.FriendProfileImageModel.ProfileImageInfo", lpparam.classLoader);
        findAndHookMethod(aClass, "a", BaseActivityClass, ProfileImageInfoClass, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                Field c = findField(ProfileImageInfoClass, "c");
                Log.d("crackQQ", "PICPATH:" + c.get(param.args[0]).toString() + ":END");
            }
        });
    }

    /*
     * Potato
     * */
    public void crackPotato_getInfo() {
        final Class<?> AddContactActivity$UserLabelClass = findClass("org.potato.ui.Contact.AddContactActivity$UserLabel", lpparam.classLoader);
        final Class<?> TLRPC$UserClass = findClass("org.potato.tgnet.TLRPC$User", lpparam.classLoader);
        findAndHookMethod(AddContactActivity$UserLabelClass, "setData", TLRPC$UserClass, boolean.class, String.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                Field first_nameField = findField(TLRPC$UserClass, "first_name");
                Field last_nameField = findField(TLRPC$UserClass, "last_name");
                Field usernameField = findField(TLRPC$UserClass, "username");
                Field idField = findField(TLRPC$UserClass, "id");
                Field phoneField = findField(TLRPC$UserClass, "phone");
                Field lang_codeField = findField(TLRPC$UserClass, "lang_code");
                Field photoField = findField(TLRPC$UserClass, "photo");
                Field restrictedField = findField(TLRPC$UserClass, "restricted");
                Field restriction_reasonField = findField(TLRPC$UserClass, "restriction_reason");
                Field statusField = findField(TLRPC$UserClass, "status");
                Field mutual_contactField = findField(TLRPC$UserClass, "mutual_contact");
                Field explicit_contentField = findField(TLRPC$UserClass, "explicit_content");
                Log.d("crackPotato",
                        " first name: " + first_nameField.get(param.args[0])
                                + ";last name: " + last_nameField.get(param.args[0])
                                + ";phone: " + phoneField.get(param.args[0])
                                + ";id: " + idField.get(param.args[0])
                                + ";status: " + statusField.get(param.args[0])
                                + ";username: " + usernameField.get(param.args[0])
                                + ";photo: " + photoField.get(param.args[0])
                                + ";lang code: " + lang_codeField.get(param.args[0])
                                + ";is restricted: " + restrictedField.get(param.args[0])
                                + ";restriction reason: " + restriction_reasonField.get(param.args[0])
                                + ";is mutual contact: " + mutual_contactField.get(param.args[0])
                                + ";is explicit content: " + explicit_contentField.get(param.args[0])
                );
            }
        });
    }
}
