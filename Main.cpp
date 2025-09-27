#include <list>
#include <vector>
#include <string.h>
#include <pthread.h>
#include <thread>
#include <cstring>
#include <jni.h>
#include <unistd.h>
#include <fstream>
#include <iostream>
#include <dlfcn.h>
#include "Includes/Logger.h"
#include "Includes/obfuscate.h"
#include "Includes/Utils.h"
#include "KittyMemory/MemoryPatch.h"
#include "Menu/Setup.h"
#include "Includes/Macros.h"

#define targetLibName OBFUSCATE("libil2cpp.so")
#define targetLibNameU OBFUSCATE("libunity.so")

const char *VipName;
const char *VipPass;

struct MemPatches
{
	MemoryPatch By1, By2, By3, By4, By5, By6, By7, By8, By9, By10,
	By11, By12, By13, By14, By15, By16, By17, By18, By19, By20,
	By21, By22, By23, ipad0 ,ipad4,
	ipad5, ipad6, ipad7, ipad8, ipad9, speedplayer0, speedplayer1,
    speedplayer2, speedplayer3, speedplayer4, speedplayer5, speedgame1, 
	speedgame2, speedgame3, multi1, multi2,multi3, multi4, multi5, multi6, multi7, multi8, multi9,
	flag1, flag2, flag3, stop1, stop2, stop3, stop4, canceleffect, hide1,hide2, hide3, 
	hide4, hide5, hide6, hide7, hide8, hide9, hide10, hide11, zoom1, NoJump1, SpeedAnim1, SpeedAnim2, Smooth1,
	Smooth2, Smooth3, Smooth4, Smooth5, Smooth6, Smooth7, Smooth8, Smooth9, 
	roger1, roger2, roger3,roger4, shanks1, shanks2, shanks3, shanks4, Longrangef15, 
	Longrangeff15, Longrangefff15, Longrangeffff15 ,aurakill1, aurakill2, NormalAura1, NormalAura2, NormalAura3, NormalAura4,NormalAura5, NormalAura6, NormalAura7, NormalAura8, NormalAura9, NormalAura10, NormalAura11, NormalAura12, NormalAura13, NormalAura14, NormalAura15, NormalAura16, NormalAura17, NormalAura18, NormalAura19, NormalAura20, BumbuAura1, BumbuAura2, BumbuAura3, BumbuAura4, BumbuAura5, BumbuAura6, BumbuAura7,BumbuAura8, BumbuAura9, BumbuAura10, BumbuAura11, BumbuAura12, BumbuAura13, BumbuAura14, BumbuAura15, BumbuAura16, BumbuAura17,BumbuAura18, BumbuAura19, wind1, wind2, 
	wind3, wind4, wind5, nofal1, nofal2, nofal3, nofal4,nofal5, nofal6, nofal7, nofal8, nofal9, nofal10, nofal11, efek1, 
	multix1, multix2, multix3, multix4, multix5, multix6, multix7, multix8, multix9, Longrangef8,
    Longrangeff8, Longrangefff8, Longrangeffff8,Longrangef3, Longrangeff3, Longrangefff3, Longrangeffff3,Longrangef2, Longrangeff2, Longrangefff2, Longrangeffff2,Longrangef1, Longrangeff1, Longrangefff1, Longrangeffff1;
    
   } hexPatches;


float IsFov = 0.0f;
float IsFov1 = 0.0f;

void (*old_Fov)(void *instance, float value);
void Fov(void *instance, float value) {
    if (instance != NULL) {
        if (IsFov > 0.0f) {
            old_Fov(instance, IsFov);
            return;
        }
    }
    return old_Fov(instance, value);
}


void (*old_Fov1)(void *instance, float value);
void Fov1(void *instance, float value) {
    if (instance != NULL) {
        if (IsFov1 > 0.0f) {
            old_Fov1(instance, IsFov1);
            return;
        }
    }
    return old_Fov1(instance, value);
}

void *hack_thread(void *) {
    LOGI(OBFUSCATE("pthread created"));
    do {
        sleep(1);
    } while (!isLibraryLoaded(targetLibName));
    LOGI(OBFUSCATE("%s has been loaded"), (const char *) targetLibName);

#if defined(__aarch64__) 
//long range
hexPatches.By1 = MemoryPatch::createWithHex("libil2cpp.so",0x1413BCC, "1F2003D5C0035FD6");
hexPatches.By2 = MemoryPatch::createWithHex("libil2cpp.so",0x1415668, "1F2003D5C0035FD6");
hexPatches.By3 = MemoryPatch::createWithHex("libil2cpp.so",0x1415670, "1F2003D5C0035FD6");
hexPatches.By4 = MemoryPatch::createWithHex("libil2cpp.so",0x18E75EC, "C0035FD6");
hexPatches.By5 = MemoryPatch::createWithHex("libil2cpp.so",0x17E6EBC, "1F2003D5C0035FD6");
hexPatches.By6 = MemoryPatch::createWithHex("libil2cpp.so",0x16B7920, "1F2003D5C0035FD6");
hexPatches.By7 = MemoryPatch::createWithHex("libil2cpp.so",0x1413358, "1F2003D5C0035FD6");
hexPatches.By8 = MemoryPatch::createWithHex("libil2cpp.so",0x16B73A0, "1F2003D5C0035FD6");
hexPatches.By9 = MemoryPatch::createWithHex("libil2cpp.so",0x1871A0C, "1F2003D5C0035FD6");
hexPatches.By10 = MemoryPatch::createWithHex("libil2cpp.so",0x12067F0, "1F2003D5C0035FD6");
hexPatches.By11 = MemoryPatch::createWithHex("libil2cpp.so",0x16F36CC, "C0035FD6");
hexPatches.By12 = MemoryPatch::createWithHex("libil2cpp.so",0x16F36DC, "1F2003D5C0035FD6");
hexPatches.By13 = MemoryPatch::createWithHex("libil2cpp.so",0x16BBEA8, "C0035FD6");
hexPatches.By14 = MemoryPatch::createWithHex("libil2cpp.so",0x12067BC, "C0035FD6");
hexPatches.By15 = MemoryPatch::createWithHex("libil2cpp.so",0x166B9D4, "C0035FD6");
hexPatches.By16 = MemoryPatch::createWithHex("libil2cpp.so",0x16FA458, "1F2003D5C0035FD6");
hexPatches.By17 = MemoryPatch::createWithHex("libil2cpp.so",0x166B2CC, "C0035FD6");
hexPatches.By18 = MemoryPatch::createWithHex("libil2cpp.so",0x1882EC8, "000080D2C0035FD6");
hexPatches.By19 = MemoryPatch::createWithHex("libil2cpp.so",0x18326B8, "000080D2C0035FD6");
hexPatches.By20 = MemoryPatch::createWithHex("libil2cpp.so",0x18C810C, "1F2003D5C0035FD6");
hexPatches.By21 = MemoryPatch::createWithHex("libil2cpp.so",0x181539C, "000080D2C0035FD6");
hexPatches.By22 = MemoryPatch::createWithHex("libil2cpp.so",0x1808BD8, "000080D2C0035FD6");
hexPatches.By23 = MemoryPatch::createWithHex("libil2cpp.so",0x334C8A0, "1F2003D5C0035FD6");


//ipad view
hexPatches.ipad4 = MemoryPatch::createWithHex("libil2cpp.so",0x16535EC, "C0035FD6");
hexPatches.ipad5 = MemoryPatch::createWithHex("libil2cpp.so",0x1654EDC, "C0035FD6");
hexPatches.ipad6 = MemoryPatch::createWithHex("libil2cpp.so",0x186E91C, "1F2003D5C0035FD6");
hexPatches.ipad7 = MemoryPatch::createWithHex("libil2cpp.so",0x33354F8, "C0035FD6");
hexPatches.ipad8 = MemoryPatch::createWithHex("libil2cpp.so",0x33354F8, "1F2003D5C0035FD6");

hexPatches.ipad9 = MemoryPatch::createWithHex("libil2cpp.so",0x186E91C, "1F2003D5C0035FD6");



	// multi hits
hexPatches.multi1 = MemoryPatch::createWithHex("libil2cpp.so",0x1824F98, "C0035FD6");
hexPatches.multi2 = MemoryPatch::createWithHex("libil2cpp.so",0x1870344, "C0035FD6");


	// stop bots
hexPatches.stop1 = MemoryPatch::createWithHex("libil2cpp.so",0x186BDB0, "200080D2C0035FD6");
hexPatches.stop2 = MemoryPatch::createWithHex("libil2cpp.so",0x17FBA60, "200080D2C0035FD6");
hexPatches.stop3 = MemoryPatch::createWithHex("libil2cpp.so",0x18018E4, "200080D2C0035FD6");
hexPatches.stop4 = MemoryPatch::createWithHex("libil2cpp.so",0x1801A54, "200080D2C0035FD6");


	// hide heal
hexPatches.hide1 = MemoryPatch::createWithHex("libil2cpp.so",0x187F6FC, "C0035FD6");
hexPatches.hide2 = MemoryPatch::createWithHex("libil2cpp.so",0x187C598, "C0035FD6");
hexPatches.hide3 = MemoryPatch::createWithHex("libil2cpp.so",0x18DEC00, "C0035FD6");
hexPatches.hide4 = MemoryPatch::createWithHex("libil2cpp.so",0x18DE770, "C0035FD6");
hexPatches.hide5 = MemoryPatch::createWithHex("libil2cpp.so",0x1784330, "C0035FD6");
hexPatches.hide6 = MemoryPatch::createWithHex("libil2cpp.so",0x1783F14, "C0035FD6");
hexPatches.hide7 = MemoryPatch::createWithHex("libil2cpp.so",0x187F6FC, "C0035FD6");
hexPatches.hide8 = MemoryPatch::createWithHex("libil2cpp.so",0x1881228, "C0035FD6");
hexPatches.hide9 = MemoryPatch::createWithHex("libil2cpp.so",0x1881228, "C0035FD6");
hexPatches.hide10 = MemoryPatch::createWithHex("libil2cpp.so",0x187F27C, "C0035FD6");
hexPatches.hide11 = 


// effect
hexPatches.efek1 = MemoryPatch::createWithHex("libil2cpp.so",0x183A4F0, "C0035FD6");

	// no fall
hexPatches.nofal1 = MemoryPatch::createWithHex("libil2cpp.so",0x18322B8, "200080D2C0035FD6");
hexPatches.nofal2 = MemoryPatch::createWithHex("libil2cpp.so",0x18E11E8, "200080D2C0035FD6");
hexPatches.nofal3 = MemoryPatch::createWithHex("libil2cpp.so",0x183C14C, "200080D2C0035FD6");
hexPatches.nofal4 = MemoryPatch::createWithHex("libil2cpp.so",0x18E1204, "200080D2C0035FD6");
hexPatches.nofal5 = MemoryPatch::createWithHex("libil2cpp.so",0x18322D8, "200080D2C0035FD6");
hexPatches.nofal6 = MemoryPatch::createWithHex("libil2cpp.so",0x183C1B8, "200080D2C0035FD6");
hexPatches.nofal7 = MemoryPatch::createWithHex("libil2cpp.so",0x183C258, "200080D2C0035FD6");
hexPatches.nofal8 = MemoryPatch::createWithHex("libil2cpp.so",0x18E1220, "200080D2C0035FD6");
hexPatches.nofal9 = MemoryPatch::createWithHex("libil2cpp.so",0x18322F8, "200080D2C0035FD6");
hexPatches.nofal10 = 
hexPatches.nofal11 = 

	// flag
hexPatches.flag1 = MemoryPatch::createWithHex("libil2cpp.so",0x16C272C, "20008052C0035FD6"); 
hexPatches.flag2 = MemoryPatch::createWithHex("libil2cpp.so",0x18560E0, "407FA8520000271EC0035FD6");


// wind
hexPatches.wind1 = MemoryPatch::createWithHex("libil2cpp.so",0x18CAD44, "200080D2C0035FD6");
hexPatches.wind2 = MemoryPatch::createWithHex("libil2cpp.so",0x1869D00, "200080D2C0035FD6");
hexPatches.wind3 = MemoryPatch::createWithHex("libil2cpp.so",0x18CAD44, "200080D2C0035FD6");
hexPatches.wind4 = 
hexPatches.wind5 = 

// speed player
hexPatches.speedplayer0 = MemoryPatch::createWithHex("libil2cpp.so",0x18332E4, "0090221EC0035FD6");
hexPatches.speedplayer1 = MemoryPatch::createWithHex("libil2cpp.so",0x18331A4, "0090241EC0035FD6");
hexPatches.speedplayer2 = MemoryPatch::createWithHex("libil2cpp.so",0x18332E4, "00F0271EC0035FD6");

// aura
hexPatches.aurakill1 = MemoryPatch::createWithHex("libil2cpp.so",0x18201B0, "1F2003D5C0035FD6");
hexPatches.aurakill2 = MemoryPatch::createWithHex("libil2cpp.so",0x181F9A8, "00 00 80 D2 C0 03 5F D6");

	// speedgame
hexPatches.speedgame1 = MemoryPatch::createWithHex("libil2cpp.so",0x33351BC, "4066861280B9A7720000271EC0035FD6");
hexPatches.speedgame2 = MemoryPatch::createWithHex("libil2cpp.so",0x33351BC,"A0CC8C1220E3A7720000271EC0035FD6");
hexPatches.speedgame3 = MemoryPatch::createWithHex("libil2cpp.so",0x33351BC, "0010211EC0035FD6");
   
// zoom
hexPatches.zoom1 = MemoryPatch::createWithHex("libil2cpp.so",0x18D0D10, "C0035FD6");

// NoJump
hexPatches.NoJump1 = MemoryPatch::createWithHex("libil2cpp.so",0x18D0DC0, "C0035FD6");

// SpeedAnim
hexPatches.SpeedAnim1 = MemoryPatch::createWithHex("libil2cpp.so",0x170AA1C,"A0CC8C1220E3A7720000271EC0035FD6");
hexPatches.SpeedAnim2 = MemoryPatch::createWithHex("libil2cpp.so",0x170C548, "C0035FD6");

// NormalAura
hexPatches.NormalAura1 = MemoryPatch::createWithHex("libil2cpp.so",0x16588AC, "000080D2C0035FD6");
hexPatches.NormalAura2 = MemoryPatch::createWithHex("libil2cpp.so",0x16588AC, "200080D2C0035FD6");

// BumbuAura
hexPatches.BumbuAura1 = MemoryPatch::createWithHex("libil2cpp.so",0x1888F34, "200080D2C0035FD6");
hexPatches.BumbuAura2 = MemoryPatch::createWithHex("libil2cpp.so",0x1657DAC, "200080D2C0035FD6");
hexPatches.BumbuAura3 = MemoryPatch::createWithHex("libil2cpp.so",0x1657DB4, "200080D2C0035FD6");
hexPatches.BumbuAura4 = MemoryPatch::createWithHex("libil2cpp.so",0x1888F3C, "200080D2C0035FD6");
hexPatches.BumbuAura5 = MemoryPatch::createWithHex("libil2cpp.so",0x1608700, "1F2003D5C0035FD6");
hexPatches.BumbuAura6 = MemoryPatch::createWithHex("libil2cpp.so",0x136E848, "1F2003D5C0035FD6");
hexPatches.BumbuAura7 = MemoryPatch::createWithHex("libil2cpp.so",0x18E22BC, "1F2003D5C0035FD6");
hexPatches.BumbuAura8 = MemoryPatch::createWithHex("libil2cpp.so",0x1832048, "1F2003D5C0035FD6");
hexPatches.BumbuAura9 = MemoryPatch::createWithHex("libil2cpp.so",0x178310C, "1F2003D5C0035FD6");
hexPatches.BumbuAura10 = MemoryPatch::createWithHex("libil2cpp.so",0x183F2E4, "1F2003D5C0035FD6");

	// long range x15
hexPatches.Longrangef15 = MemoryPatch::createWithHex("libunity.so",0xfb5ab8, "00007041");
hexPatches.Longrangeff15 = MemoryPatch::createWithHex("libunity.so",0xfb5ab4, "00007041");
hexPatches.Longrangefff15 = MemoryPatch::createWithHex("libunity.so",0xFfb5ab0, "00007041");
hexPatches.Longrangeffff15 = MemoryPatch::createWithHex("libunity.so",0xfb5aac, "00007041");

// long range x1,5
hexPatches.Longrangef3 = MemoryPatch::createWithHex("libunity.so",0xfb5ab8, "0000C03F");
hexPatches.Longrangeff3 = MemoryPatch::createWithHex("libunity.so",0xfb5ab4, "0000C03F");
hexPatches.Longrangefff3 = MemoryPatch::createWithHex("libunity.so",0xfb5ab0, "0000C03F");
hexPatches.Longrangeffff3 = MemoryPatch::createWithHex("libunity.so",0xfb5aac, "0000C03F");

// long range x2
hexPatches.Longrangef2 = MemoryPatch::createWithHex("libunity.so",0xfb5ab8, "00000040");
hexPatches.Longrangeff2 = MemoryPatch::createWithHex("libunity.so",0xfb5ab4, "00000040");
hexPatches.Longrangefff2 = MemoryPatch::createWithHex("libunity.so",0xfb5ab0, "00000040");
hexPatches.Longrangeffff2 = MemoryPatch::createWithHex("libunity.so",0xfb5aac, "00000040");

// long range x1,2
hexPatches.Longrangef1 = MemoryPatch::createWithHex("libunity.so",0xfb5ab8, "9A99993F");
hexPatches.Longrangeff1 = MemoryPatch::createWithHex("libunity.so",0xfb5ab4, "9A99993F");
hexPatches.Longrangefff1 = MemoryPatch::createWithHex("libunity.so",0xfb5ab0, "9A99993F");
hexPatches.Longrangeffff1 = MemoryPatch::createWithHex("libunity.so",0xfb5aac, "9A99993F");

// long range x8
hexPatches.Longrangef8 = MemoryPatch::createWithHex("libunity.so",0xfb5ab8, "00000041");
hexPatches.Longrangeff8 = MemoryPatch::createWithHex("libunity.so",0xfb5ab4, "00000041");
hexPatches.Longrangefff8 = MemoryPatch::createWithHex("libunity.so",0xfb5ab0, "00000041");
hexPatches.Longrangeffff8 = MemoryPatch::createWithHex("libunity.so",0xfb5aac, "00000041");

//camera pov
A64HookFunction((void *)getAbsoluteAddress("libil2cpp.so", 0x162DD70), (void *) Fov, (void **) &old_Fov);
A64HookFunction((void *)getAbsoluteAddress("libil2cpp.so", 0x162DD70), (void *) Fov1, (void **) &old_Fov1);

#else // 32bit
    LOGI(OBFUSCATE("Done"));
#endif
    return NULL;
}


jobjectArray GetUserType(JNIEnv *env, jobject context) {
    jobjectArray ret;

    const char *features[] = {
				OBFUSCATE("-111_InputText_تسجيل دخول🌫️"),
				OBFUSCATE("-333_Button_Login")
		
		  };

    //Now you dont have to manually update the number everytime;
    int Total_Feature = (sizeof features / sizeof features[0]);
    ret = (jobjectArray)
            env->NewObjectArray(Total_Feature, env->FindClass(OBFUSCATE("java/lang/String")),
                                env->NewStringUTF(""));

    for (int i = 0; i < Total_Feature; i++)
        env->SetObjectArrayElement(ret, i, env->NewStringUTF(features[i]));

    return (ret);
}

extern "C"
JNIEXPORT jobjectArray JNICALL
Java_com_android_support_Menu_Vipdata(JNIEnv *env, jobject thiz) {
    // Create a new Java String array of size 2
    jobjectArray result = env->NewObjectArray(2, env->FindClass("java/lang/String"), nullptr);

    // Set VipName and VipPass in the array
    env->SetObjectArrayElement(result, 0, env->NewStringUTF(VipName));
    env->SetObjectArrayElement(result, 1, env->NewStringUTF(VipPass));

    return result;
}



jobjectArray GetFeatureList(JNIEnv *env, jobject context) {
    jobjectArray ret;

    const char *features[] = {
    OBFUSCATE("Category_اهلا بك في مود مينو ياوي"),
    OBFUSCATE("200_SeekBar_Camera POV_1_100"),
OBFUSCATE("1_Toggle_Bypass"),
OBFUSCATE("2_Toggle_Ipad View"),
OBFUSCATE("39_Toggle_Ipad View v2"),
OBFUSCATE("3_Toggle_Multi Hits متعدد "),
OBFUSCATE("5_Toggle_Stop Bots إيقاف بوتات"),
OBFUSCATE("6_Toggle_Hide اخفاء دمج"),
OBFUSCATE("7_Toggle_Cancel Effect تأثير"),
OBFUSCATE("8_Toggle_No Fall عدم سقوط"),
OBFUSCATE("9_Toggle_Flag اعلام"),
OBFUSCATE("10_Toggle_Wind رياح"),
OBFUSCATE("35_Toggle_NormalAura ملكي مستمر"),
OBFUSCATE("36_Toggle_Combo Aura"),


OBFUSCATE("Category_RISK FEATURE"),

OBFUSCATE("11_Toggle_Long Range x15"),
OBFUSCATE("12_Toggle_Long Range x8"),
OBFUSCATE("26_Toggle_Long Range x2"),
OBFUSCATE("13_Toggle_Long Range x1,5"),
OBFUSCATE("34_Toggle_Long Range x1,2"),
OBFUSCATE("14_Toggle_Speed player سرعه x1"),
OBFUSCATE("22_Toggle_Speed player سريع x2"),
OBFUSCATE("23_Toggle_Speed player  سرععx3"),
OBFUSCATE("15_Toggle_Unlimited Aura"),
OBFUSCATE("16_Toggle_Speed Game x2"),
OBFUSCATE("17_Toggle_Speed Game x3"),
OBFUSCATE("18_Toggle_Speed Game x5"),
OBFUSCATE("19_Toggle_Zoom تكبير"),
OBFUSCATE("24_Toggle_NoJump لاقفز"),
OBFUSCATE("25_Toggle_SpeedAnim سرعه انميشن"),
        
    };

    int Total_Feature = (sizeof features / sizeof features[0]);
    ret = (jobjectArray)
            env->NewObjectArray(Total_Feature, env->FindClass(OBFUSCATE("java/lang/String")),
                                env->NewStringUTF(""));
    for (int i = 0; i < Total_Feature; i++)
        env->SetObjectArrayElement(ret, i, env->NewStringUTF(features[i]));

    return (ret);
}
void Changes(JNIEnv *env, jclass clazz, jobject obj,
                                        jint featNum, jstring featName, jint value,
                                        jboolean boolean, jstring str) {
    LOGD(OBFUSCATE("Feature name: %d - %s | Value: = %d | Bool: = %d | Text: = %s"), featNum,
         env->GetStringUTFChars(featName, 0), value,
         boolean, str != NULL ? env->GetStringUTFChars(str, 0) : "");

    switch (featNum) {
    case -111:
			VipName = env->GetStringUTFChars(str, 0);
			break;
		case -222:
			VipPass = env->GetStringUTFChars(str, 0);
			break;
   
       case 1:
if (boolean)
{
	hexPatches.By1.Modify();
	hexPatches.By2.Modify();
	hexPatches.By3.Modify();
	hexPatches.By4.Modify();
	hexPatches.By6.Modify();
	hexPatches.By7.Modify();
	hexPatches.By8.Modify();
	hexPatches.By9.Modify();
	hexPatches.By10.Modify();
	hexPatches.By11.Modify();
	hexPatches.By12.Modify();
	hexPatches.By13.Modify();
	hexPatches.By14.Modify();
	hexPatches.By15.Modify();
	hexPatches.By16.Modify();
	hexPatches.By17.Modify();
	hexPatches.By18.Modify();
	hexPatches.By19.Modify();
	hexPatches.By20.Modify();
	hexPatches.By21.Modify();
	hexPatches.By22.Modify();
	hexPatches.By23.Modify();
}
else
{
	hexPatches.By1.Restore();
	hexPatches.By2.Restore();
	hexPatches.By3.Restore();
	hexPatches.By4.Restore();
	hexPatches.By6.Restore();
	hexPatches.By7.Restore();
	hexPatches.By8.Restore();
	hexPatches.By9.Restore();
	hexPatches.By11.Restore();
	hexPatches.By12.Restore();
	hexPatches.By13.Restore();
	hexPatches.By14.Restore();
	hexPatches.By15.Restore();
	hexPatches.By16.Restore();
	hexPatches.By17.Restore();
	hexPatches.By18.Restore();
	hexPatches.By19.Restore();
	hexPatches.By20.Restore();
	hexPatches.By21.Restore();
	hexPatches.By22.Restore();
	hexPatches.By23.Restore();
}
break;
			case 2:
        if (boolean)
		{
	hexPatches.ipad4.Modify();
	hexPatches.ipad5.Modify();
	hexPatches.ipad6.Modify();
	hexPatches.ipad7.Modify();
	hexPatches.ipad8.Modify();
}
else
{
	hexPatches.ipad4.Restore();
	hexPatches.ipad5.Restore();
	hexPatches.ipad6.Restore();
	hexPatches.ipad7.Restore();
	hexPatches.ipad8.Restore();
}
break;


case 39:
if (boolean)
{
	hexPatches.ipad9.Modify();
}
else
{
	hexPatches.ipad9.Restore();
}
break;
        
        
	case 3:
if (boolean)
{
	hexPatches.multi1.Modify();
	hexPatches.multi2.Modify();
}
else
{
	hexPatches.multi1.Restore();
	hexPatches.multi2.Restore();
}
break;


	


	case 5:
if (boolean)
{
	hexPatches.stop1.Modify();
	hexPatches.stop2.Modify();
	hexPatches.stop3.Modify();
	hexPatches.stop4.Modify();
}
else
{
	hexPatches.stop1.Restore();
	hexPatches.stop2.Restore();
	hexPatches.stop3.Restore();
	hexPatches.stop4.Restore();
}
break;

case 6:
if (boolean)
{
	hexPatches.hide1.Modify();
	hexPatches.hide2.Modify();
	hexPatches.hide3.Modify();
	hexPatches.hide4.Modify();
	hexPatches.hide5.Modify();
	hexPatches.hide6.Modify();
	hexPatches.hide7.Modify();
	hexPatches.hide8.Modify();
	hexPatches.hide9.Modify();
	hexPatches.hide10.Modify();
	hexPatches.hide11.Modify();
}
else
{
	hexPatches.hide1.Restore();
	hexPatches.hide2.Restore();
	hexPatches.hide3.Restore();
	hexPatches.hide4.Restore();
	hexPatches.hide5.Restore();
	hexPatches.hide6.Restore();
	hexPatches.hide7.Restore();
	hexPatches.hide8.Restore();
	hexPatches.hide9.Restore();
	hexPatches.hide10.Restore();
	hexPatches.hide11.Restore();
}
break;


	
	case 7:
if (boolean)
{
	hexPatches.efek1.Modify();
}
else
{
	hexPatches.efek1.Restore();
}
break;



	case 8:
if (boolean)
{
	hexPatches.nofal1.Modify();
	hexPatches.nofal2.Modify();
	hexPatches.nofal3.Modify();
	hexPatches.nofal4.Modify();
	hexPatches.nofal5.Modify();
	hexPatches.nofal6.Modify();
	hexPatches.nofal7.Modify();
	hexPatches.nofal8.Modify();
	hexPatches.nofal9.Modify();
	hexPatches.nofal10.Modify();
	hexPatches.nofal11.Modify();
}
else
{
	hexPatches.nofal1.Restore();
	hexPatches.nofal2.Restore();
	hexPatches.nofal3.Restore();
	hexPatches.nofal4.Restore();
	hexPatches.nofal5.Restore();
	hexPatches.nofal6.Restore();
	hexPatches.nofal7.Restore();
	hexPatches.nofal8.Restore();
	hexPatches.nofal9.Restore();
	hexPatches.nofal10.Restore();
	hexPatches.nofal11.Restore();
}
break;

case 9:
if (boolean)
{
	hexPatches.flag1.Modify();
	hexPatches.flag2.Modify();
}
else
{
	hexPatches.flag1.Restore();
	hexPatches.flag2.Restore();
}
break;

	case 10:
if (boolean)
{
	hexPatches.wind1.Modify();
	hexPatches.wind2.Modify();
	hexPatches.wind3.Modify();
	hexPatches.wind4.Modify();
	hexPatches.wind5.Modify();
}
else
{
	hexPatches.wind1.Restore();
	hexPatches.wind2.Restore();
	hexPatches.wind3.Restore();
	hexPatches.wind4.Restore();
	hexPatches.wind5.Restore();
}
break;


case 35:
if (boolean)
{
	hexPatches.NormalAura1.Modify();
	hexPatches.NormalAura2.Modify();
	hexPatches.NormalAura3.Modify();
	hexPatches.NormalAura4.Modify();
	hexPatches.NormalAura5.Modify();
	hexPatches.NormalAura6.Modify();
	hexPatches.NormalAura7.Modify();
	hexPatches.NormalAura8.Modify();
	hexPatches.NormalAura9.Modify();
	hexPatches.NormalAura10.Modify();
	hexPatches.NormalAura11.Modify();
	hexPatches.NormalAura12.Modify();
	hexPatches.NormalAura13.Modify();
	hexPatches.NormalAura14.Modify();
	hexPatches.NormalAura15.Modify();
	hexPatches.NormalAura16.Modify();
	hexPatches.NormalAura17.Modify();
	hexPatches.NormalAura18.Modify();
	hexPatches.NormalAura19.Modify();
	hexPatches.NormalAura20.Modify();
}
else
{
	hexPatches.NormalAura1.Restore();
	hexPatches.NormalAura2.Restore();
	hexPatches.NormalAura3.Restore();
	hexPatches.NormalAura4.Restore();
	hexPatches.NormalAura5.Restore();
	hexPatches.NormalAura6.Restore();
	hexPatches.NormalAura7.Restore();
	hexPatches.NormalAura8.Restore();
	hexPatches.NormalAura9.Restore();
	hexPatches.NormalAura10.Restore();
	hexPatches.NormalAura11.Restore();
	hexPatches.NormalAura12.Restore();
	hexPatches.NormalAura13.Restore();
	hexPatches.NormalAura14.Restore();
	hexPatches.NormalAura15.Restore();
	hexPatches.NormalAura16.Restore();
	hexPatches.NormalAura17.Restore();
	hexPatches.NormalAura18.Restore();
	hexPatches.NormalAura19.Restore();
	hexPatches.NormalAura20.Restore();
}
break;


case 36:
if (boolean)
{
	hexPatches.BumbuAura1.Modify();
	hexPatches.BumbuAura2.Modify();
	hexPatches.BumbuAura3.Modify();
	hexPatches.BumbuAura4.Modify();
	hexPatches.BumbuAura5.Modify();
	hexPatches.BumbuAura6.Modify();
	hexPatches.BumbuAura7.Modify();
	hexPatches.BumbuAura8.Modify();
	hexPatches.BumbuAura9.Modify();
	hexPatches.BumbuAura10.Modify();
}
else
{
	hexPatches.BumbuAura1.Restore();
	hexPatches.BumbuAura2.Restore();
	hexPatches.BumbuAura3.Restore();
	hexPatches.BumbuAura4.Restore();
	hexPatches.BumbuAura5.Restore();
	hexPatches.BumbuAura6.Restore();
	hexPatches.BumbuAura7.Restore();
	hexPatches.BumbuAura8.Restore();
	hexPatches.BumbuAura9.Restore();
	hexPatches.BumbuAura10.Restore();
}
break;

//-----------------------fitur risk bawah-----------------------

case 11:
if (boolean)
{
	hexPatches.Longrangef15.Modify();
	hexPatches.Longrangeff15.Modify();
	hexPatches.Longrangefff15.Modify();
	hexPatches.Longrangeffff15.Modify();
}
else
{
	hexPatches.Longrangef15.Restore();
	hexPatches.Longrangeff15.Restore();
	hexPatches.Longrangefff15.Restore();
	hexPatches.Longrangeffff15.Restore();
}
break;

case 12:
if (boolean)
{
	hexPatches.Longrangef8.Modify();
	hexPatches.Longrangeff8.Modify();
	hexPatches.Longrangefff8.Modify();
	hexPatches.Longrangeffff8.Modify();
}
else
{
	hexPatches.Longrangef8.Restore();
	hexPatches.Longrangeff8.Restore();
	hexPatches.Longrangefff8.Restore();
	hexPatches.Longrangeffff8.Restore();
}
break;


case 13:
if (boolean)
{
	hexPatches.Longrangef3.Modify();
	hexPatches.Longrangeff3.Modify();
	hexPatches.Longrangefff3.Modify();
	hexPatches.Longrangeffff3.Modify();
}
else
{
	hexPatches.Longrangef3.Restore();
	hexPatches.Longrangeff3.Restore();
	hexPatches.Longrangefff3.Restore();
	hexPatches.Longrangeffff3.Restore();
}
break;


case 26:
if (boolean)
{
	hexPatches.Longrangef2.Modify();
	hexPatches.Longrangeff2.Modify();
	hexPatches.Longrangefff2.Modify();
	hexPatches.Longrangeffff2.Modify();
}
else
{
	hexPatches.Longrangef2.Restore();
	hexPatches.Longrangeff2.Restore();
	hexPatches.Longrangefff2.Restore();
	hexPatches.Longrangeffff2.Restore();
}
break;


case 34:
if (boolean)
{
	hexPatches.Longrangef1.Modify();
	hexPatches.Longrangeff1.Modify();
	hexPatches.Longrangefff1.Modify();
	hexPatches.Longrangeffff1.Modify();
}
else
{
	hexPatches.Longrangef1.Restore();
	hexPatches.Longrangeff1.Restore();
	hexPatches.Longrangefff1.Restore();
	hexPatches.Longrangeffff1.Restore();
}
break;




case 14:
if (boolean)
{
	hexPatches.speedplayer0.Modify();
}
else
{
	hexPatches.speedplayer0.Restore();
}
break;



case 22:
if (boolean)
{
	hexPatches.speedplayer1.Modify();
}
else
{
    hexPatches.speedplayer1.Restore();
}
break;

case 23:
if (boolean)
{
	hexPatches.speedplayer2.Modify();
}
else
{
    hexPatches.speedplayer2.Restore();
}
break;






case 15:
if (boolean)
{
	hexPatches.aurakill1.Modify();
	hexPatches.aurakill2.Modify();
}
else
{
	hexPatches.aurakill1.Restore();
	hexPatches.aurakill2.Restore();
}
break;


	case 16:
if (boolean)
{
	hexPatches.speedgame1.Modify();
}
else
{
	hexPatches.speedgame1.Restore();
}
break;

	case 17:
if (boolean)
{
	hexPatches.speedgame2.Modify();
}
else
{
	hexPatches.speedgame2.Restore();
}
break;

	case 18:
if (boolean)
{
	hexPatches.speedgame3.Modify();
}
else
{
	hexPatches.speedgame3.Restore();
}
break;


case 19:
if (boolean)
{
	hexPatches.zoom1.Modify();
}
else
{
	hexPatches.zoom1.Restore();
}
break;



case 24:
if (boolean)
{
	hexPatches.NoJump1.Modify();
}
else
{
	hexPatches.NoJump1.Restore();
}
break;


case 25:
if (boolean)
{
	hexPatches.SpeedAnim1.Modify();
	hexPatches.SpeedAnim2.Modify();
}
else
{
	hexPatches.SpeedAnim1.Restore();
	hexPatches.SpeedAnim2.Restore();
}
break;

           
           
case 200:
    IsFov = value;
    IsFov1 = value;
    
    break;

           
    }
}

__attribute__((constructor))
void lib_main() {
    // Create a new thread so it does not block the main thread, means the game would not freeze
    pthread_t ptid;
    pthread_create(&ptid, NULL, hack_thread, NULL);
}

int RegisterMenu(JNIEnv *env) {
    JNINativeMethod methods[] = {
            {OBFUSCATE("Icon"), OBFUSCATE("()Ljava/lang/String;"), reinterpret_cast<void *>(Icon)},
            {OBFUSCATE("IconWebViewData"),  OBFUSCATE("()Ljava/lang/String;"), reinterpret_cast<void *>(IconWebViewData)},
            {OBFUSCATE("IsGameLibLoaded"),  OBFUSCATE("()Z"), reinterpret_cast<void *>(isGameLibLoaded)},
            {OBFUSCATE("Init"),  OBFUSCATE("(Landroid/content/Context;Landroid/widget/TextView;Landroid/widget/TextView;)V"), reinterpret_cast<void *>(Init)},
            {OBFUSCATE("SettingsList"),  OBFUSCATE("()[Ljava/lang/String;"), reinterpret_cast<void *>(SettingsList)},
            {OBFUSCATE("GetFeatureList"),  OBFUSCATE("()[Ljava/lang/String;"), reinterpret_cast<void *>(GetFeatureList)},
            {OBFUSCATE("GetUserType"),  OBFUSCATE("()[Ljava/lang/String;"), reinterpret_cast<void *>(GetUserType)},
    };

    jclass clazz = env->FindClass(OBFUSCATE("com/android/support/Menu"));
    if (!clazz)
        return JNI_ERR;
    if (env->RegisterNatives(clazz, methods, sizeof(methods) / sizeof(methods[0])) != 0)
        return JNI_ERR;
    return JNI_OK;
}

int RegisterPreferences(JNIEnv *env) {
    JNINativeMethod methods[] = {
            {OBFUSCATE("Changes"), OBFUSCATE("(Landroid/content/Context;ILjava/lang/String;IZLjava/lang/String;)V"), reinterpret_cast<void *>(Changes)},
    };
    jclass clazz = env->FindClass(OBFUSCATE("com/android/support/Preferences"));
    if (!clazz)
        return JNI_ERR;
    if (env->RegisterNatives(clazz, methods, sizeof(methods) / sizeof(methods[0])) != 0)
        return JNI_ERR;
    return JNI_OK;
}

int RegisterMain(JNIEnv *env) {
    JNINativeMethod methods[] = {
            {OBFUSCATE("CheckOverlayPermission"), OBFUSCATE("(Landroid/content/Context;)V"), reinterpret_cast<void *>(CheckOverlayPermission)},
    };
    jclass clazz = env->FindClass(OBFUSCATE("com/android/support/Main"));
    if (!clazz)
        return JNI_ERR;
    if (env->RegisterNatives(clazz, methods, sizeof(methods) / sizeof(methods[0])) != 0)
        return JNI_ERR;

    return JNI_OK;
}

extern "C"
JNIEXPORT jint JNICALL
JNI_OnLoad(JavaVM *vm, void *reserved) {
    JNIEnv *env;
    vm->GetEnv((void **) &env, JNI_VERSION_1_6);
    if (RegisterMenu(env) != 0)
        return JNI_ERR;
    if (RegisterPreferences(env) != 0)
        return JNI_ERR;
    if (RegisterMain(env) != 0)
        return JNI_ERR;
    return JNI_VERSION_1_6;
}
