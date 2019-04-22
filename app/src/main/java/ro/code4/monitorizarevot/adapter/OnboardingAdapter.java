package ro.code4.monitorizarevot.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import ro.code4.monitorizarevot.net.model.OnboardingItem;

import ro.code4.monitorizarevot.R;

public class OnboardingAdapter extends PagerAdapter {
    private Context context;
    private List<OnboardingItem> items;

    public OnboardingAdapter(Context context, List<OnboardingItem> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.item_onboarding_page, container, false);

        OnboardingItem item = items.get(position);

        TextView onboardingTitle = view.findViewById(R.id.onboarding_title);
        ImageView onboardingIcon = view.findViewById(R.id.onboarding_icon);
        TextView onboardingDescription = view.findViewById(R.id.onboarding_description);

        onboardingTitle.setText(item.getTitle());
        onboardingIcon.setImageResource(item.getImageResource());
        onboardingDescription.setText(item.getDescription());

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout) object);
    }
}
