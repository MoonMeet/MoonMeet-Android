package org.mark.moonmeet;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;

import org.mark.moonmeet.ui.BaseFragment;

public class TermsAndPrivacyActivity extends BaseFragment {

    private String Terms_of_services = "";
    private String Privacy_Policy = "";

    private NestedScrollView scroller;
    private LinearLayout topbar;
    private ImageView imageview1;
    private TextView topbar_txt;
    private LinearLayout framelayout;
    private TextView privacy_policy;
    private TextView privacy_policy_txt;
    private LinearLayout divider;
    private TextView terms_and_conditions;
    private TextView terms_and_conditions_txt;

    private void initialize(Context context) {
        imageview1 = findViewById(R.id.imageview1);
        topbar_txt = findViewById(R.id.topbar_txt);
        scroller = findViewById(R.id.scroller);
        topbar = findViewById(R.id.topbar);
        framelayout = findViewById(R.id.framelayout);
        privacy_policy = findViewById(R.id.privacy_policy);
        privacy_policy_txt = findViewById(R.id.privacy_policy_txt);
        divider = findViewById(R.id.divider);
        terms_and_conditions = findViewById(R.id.terms_and_conditions);
        terms_and_conditions_txt = findViewById(R.id.terms_and_conditions_txt);
        imageview1.setOnClickListener(_view -> finishFragment());
    }

    private void initializeLogic() {
        imageview1.setColorFilter(0xFF193566, PorterDuff.Mode.MULTIPLY);
        topbar.setElevation(2);
        RelativeLayout rl = new RelativeLayout(getParentActivity());
        rl.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
        framelayout.addView(rl);
        Terms_of_services = "By downloading or using the app, these terms will automatically apply to you – you should make sure therefore that you read them carefully before using the app. You’re not allowed to copy, or modify the app, any part of the app, or our trademarks in any way. You’re not allowed to attempt to extract the source code of the app, and you also shouldn’t try to translate the app into other languages, or make derivative versions. The app itself, and all the trade marks, copyright, database rights and other intellectual property rights related to it, still belong to Rayen Mark.\n\nRayen Mark is committed to ensuring that the app is as useful and efficient as possible. For that reason, we reserve the right to make changes to the app or to charge for its services, at any time and for any reason. We will never charge you for the app or its services without making it very clear to you exactly what you’re paying for.\n\nThe Moon Meet app stores and processes personal data that you have provided to us, in order to provide my Service. It’s your responsibility to keep your phone and access to the app secure. We therefore recommend that you do not jailbreak or root your phone, which is the process of removing software restrictions and limitations imposed by the official operating system of your device. It could make your phone vulnerable to malware/viruses/malicious programs, compromise your phone’s security features and it could mean that the miz app won’t work properly or at all.\n\nThe app does use third party services that declare their own Terms and Conditions.\n\nTerms and Conditions of third party service providers used by the app are bound to owners.\n\nFirebase.   Google Play Services.               \n\nYou should be aware that there are certain things that Rayen Mark will not take responsibility for. Certain functions of the app will require the app to have an active internet connection. The connection can be Wi-Fi, or provided by your mobile network provider, but Rayen Mark cannot take responsibility for the app not working at full functionality if you don’t have access to Wi-Fi, and you don’t have any of your data allowance left.\n\nIf you’re using the app outside of an area with Wi-Fi, you should remember that your terms of the agreement with your mobile network provider will still apply. As a result, you may be charged by your mobile provider for the cost of data for the duration of the connection while accessing the app, or other third party charges. In using the app, you’re accepting responsibility for any such charges, including roaming data charges if you use the app outside of your home territory (i.e. region or country) without turning off data roaming. If you are not the bill payer for the device on which you’re using the app, please be aware that we assume that you have received permission from the bill payer for using the app.\n\nAlong the same lines, Rayen Mark cannot always take responsibility for the way you use the app i.e. You need to make sure that your device stays charged – if it runs out of battery and you can’t turn it on to avail the Service, Rayen Mark cannot accept responsibility.\n\nWith respect to Rayen Mark’s responsibility for your use of the app, when you’re using the app, it’s important to bear in mind that although we endeavour to ensure that it is updated and correct at all times, we do rely on third parties to provide information to us so that we can make it available to you. Rayen Mark accepts no liability for any loss, direct or indirect, you experience as a result of relying wholly on this functionality of the app.\n\nAt some point, we may wish to update the app. The app is currently available on Android – the requirements for system(and for any additional systems we decide to extend the availability of the app to) may change, and you’ll need to download the updates if you want to keep using the app. Rayen Mark does not promise that it will always update the app so that it is relevant to you and/or works with the Android version that you have installed on your device. However, you promise to always accept updates to the application when offered to you, We may also wish to stop providing the app, and may terminate use of it at any time without giving notice of termination to you. Unless we tell you otherwise, upon any termination, (a) the rights and licenses granted to you in these terms will end; (b) you must stop using the app, and (if needed) delete it from your device.\n\nChanges to This Terms and Conditions:\n\nI may update our Terms and Conditions from time to time. Thus, you are advised to review this page periodically for any changes. I will notify you of any changes by posting the new Terms and Conditions on this page.\n\nThese terms and conditions are effective as of 23-11-2020.\n\nContact Us:\n\nIf you have any questions or suggestions about my Terms and Conditions, do not hesitate to contact me at Rayensbai2@gmail.com.";
        Privacy_Policy = "Rayen Mark built the Moon Meet app as a Free app. This SERVICE is provided by Rayen Mark at no cost and is intended for use as is.\n\nThis page is used to inform visitors regarding my policies with the collection, use, and disclosure of Personal Information if anyone decided to use my Service.\n\nIf you choose to use my Service, then you agree to the collection and use of information in relation to this policy. The Personal Information that I collect is used for providing and improving the Service. I will not use or share your information with anyone except as described in this Privacy Policy.\n\nThe terms used in this Privacy Policy have the same meanings as in our Terms and Conditions, which is accessible at Moon Meet unless otherwise defined in this Privacy Policy.\n\nInformation Collection and Use:\n\nFor a better experience, while using our Service, I may require you to provide us with certain personally identifiable information, including but not limited to We only collect message or profile picture reports. The information that I request will be retained on your device and is not collected by me in any way.\n\nThe app does use third party services that may collect information used to identify you.\n\nReference to third party service providers used by the app are below;\n\nFirebase.   Google Play Services.               \n\nLog Data:\n\nI want to inform you that whenever you use my Service, in a case of an error in the app I collect data and information (through third party products) on your phone called Log Data. This Log Data may include information such as your device Internet Protocol (“IP”) address, device name, operating system version, the configuration of the app when utilizing my Service, the time and date of your use of the Service, and other statistics.\n\nCookies:\n\nCookies are files with a small amount of data that are commonly used as anonymous unique identifiers. These are sent to your browser from the websites that you visit and are stored on your device's internal memory.\n\nThis Service does not use these “cookies” explicitly. However, the app may use third party code and libraries that use “cookies” to collect information and improve their services. You have the option to either accept or refuse these cookies and know when a cookie is being sent to your device. If you choose to refuse our cookies, you may not be able to use some portions of this Service.\n\nService Providers:\n\nI may employ third-party companies and individuals due to the following reasons:\n\nTo facilitate our Service;To provide the Service on our behalf;To perform Service-related services; or To assist us in analyzing how our Service is used.\n\nI want to inform users of this Service that these third parties have access to your Personal Information. The reason is to perform the tasks assigned to them on our behalf. However, they are obligated not to disclose or use the information for any other purpose.\n\nSecurity:\n\nI value your trust in providing us your Personal Information, thus we are striving to use commercially acceptable means of protecting it. But remember that no method of transmission over the internet, or method of electronic storage is 100% secure and reliable, and I cannot guarantee its absolute security.\n\nLinks to Other Sites:\n\nThis Service may contain links to other sites. If you click on a third-party link, you will be directed to that site. Note that these external sites are not operated by me. Therefore, I strongly advise you to review the Privacy Policy of these websites. I have no control over and assume no responsibility for the content, privacy policies, or practices of any third-party sites or services.\n\nChildren’s Privacy:\n\nThese Services do not address anyone under the age of 13. I do not knowingly collect personally identifiable information from children under 13. In a case I discover that a child under 13 has provided me with personal information, I immediately delete this from our servers. If you are a parent or guardian and you are aware that your child has provided us with personal information, please contact me so that I will be able to take necessary actions.\n\nChanges to This Privacy Policy:\n\nI may update our Privacy Policy from time to time. Thus, you are advised to review this page periodically for any changes. I will notify you of any changes by posting the new Privacy Policy on this page.\n\nThis policy is effective as of 23-11-2020\n\nContact Us:\n\nIf you have any questions or suggestions about my Privacy Policy, do not hesitate to contact me at Rayensbai2@gmail.com";
        privacy_policy_txt.setText(Privacy_Policy);
        terms_and_conditions_txt.setText(Terms_of_services);
    }

    @Override
    public View createView(Context context) {
        fragmentView = new FrameLayout(context);
        actionBar.setAddToContainer(false);
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.termsandprivacy, (ViewGroup) fragmentView, false);
        ((ViewGroup) fragmentView).addView(view);
        initialize(context);
        initializeLogic();
        return fragmentView;
    }
}
