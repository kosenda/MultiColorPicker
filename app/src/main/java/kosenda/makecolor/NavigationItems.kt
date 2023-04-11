package kosenda.makecolor

import kosenda.makecolor.core.resource.R

sealed class NavigationItems(var route: String, var icon: Int?) {
    // Drawer
    object Picker : NavigationItems("picker", R.drawable.ic_baseline_colorize_24)
    object Seekbar : NavigationItems("seekbar", R.drawable.ic_baseline_tune_24)
    object Text : NavigationItems("text", R.drawable.ic_baseline_text_fields_24)
    object Picture : NavigationItems("picture", R.drawable.ic_baseline_image_24)
    object Merge : NavigationItems("merge", R.drawable.ic_baseline_compare_arrows_24)
    object Random : NavigationItems("random", R.drawable.ic_baseline_question_mark_24)
    object Data : NavigationItems("data", R.drawable.ic_baseline_library_books_24)
    object Split : NavigationItems("split", R.drawable.ic_baseline_dashboard_customize_24)
    object Gradation : NavigationItems("gradation", R.drawable.ic_baseline_gradient_24)
    object Review : NavigationItems("review", R.drawable.ic_baseline_rate_review_24)
    object Setting : NavigationItems("setting", R.drawable.ic_baseline_settings_24)

    // Not Drawer
    object Register : NavigationItems("register", null)
    object FullColor : NavigationItems("full_color", null)
    object SelectColor : NavigationItems("select_color", null)
    object ColorDetail : NavigationItems("color_detail", null)
    object CategoryDetail : NavigationItems("category_detail", null)
    object SplitColor : NavigationItems("split_color", null)
    object GradationColor : NavigationItems("gradation_color", null)
}
