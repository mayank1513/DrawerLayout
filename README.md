# DrawerLayout
Light weight custom layout that elegantly replaces drawer layout.

# How to Use DrawerLayout

1. Add drawer_layout.xml and right_drawer_layout.xml to your layout resoource folder. You can edit these files later to suit your purpose.
2. Add ic_launcher.png and background.xml to your drawable resources folder.
3. colors.xml and dimen.xml files to your values resource folder.
4. Now in your activity's main layout file you can add the drawer layout as follows

    <FrameLayout android:id="@+id/background"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="@drawable/background">
       <!--TODO: replace your_package with appropreate package name -->
        <your_package.DrawerLayout
            android:id="@+id/drawer"
            android:layout_width="match_parent"
            android:layout_height="match_parent"/>
    </FrameLayout>
    
 If you want to add drawer on right-side add android:gravity="right" attribute to DrawerLayout.
 
 5. Finally, in your activity after setContentView() add following lines
     
     DrawerLayout drawer = findViewById(R.id.drawer);
     drawer.background = findViewById(R.id.background);
     
Now you are all set. Your drawer is ready, you can also open or close drawer programatically by calling drawer.collapse() or drawer.expand();
