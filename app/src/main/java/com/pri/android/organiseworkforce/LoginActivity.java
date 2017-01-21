package com.pri.android.organiseworkforce;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{
    public static GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 1;
    private SignInButton mEmployeeLogin;
    private SignInButton mEmployerLogin;
    private int mButtonPressed;
    private static final String TAG = "LoginActivity";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mEmployeesRef;
    private DatabaseReference mEmployersRef;
    private FirebaseUser mUser;
    private UserObject mCurrentUser;
    private ProgressDialog mProgreeDialog;
    private boolean needToSignOut = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(getResources().getString(R.string.google_auth_key_2))
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mEmployeesRef = mFirebaseDatabase.getReference().child("employees");
        mEmployersRef = mFirebaseDatabase.getReference().child("employers");

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                mUser = firebaseAuth.getCurrentUser();
                if(mUser != null){
                    //user is signed in
                    initializeSignIn();
                    if (!needToSignOut) {        //TODO check reason for implementation
                        Intent intent = new Intent(LoginActivity.this, EmployerMainActivity.class);
                        intent.putExtra("currentUser", mCurrentUser);
                        supportFinishAfterTransition();
                        startActivity(intent);
                    }
                }else{
                    //user is signed out
                }
            }
        };
    }

    private void init() {
        mEmployeeLogin = (SignInButton)findViewById(R.id.employee_sign_in_button);
        mEmployerLogin = (SignInButton)findViewById(R.id.employer_sign_in_button);

        mEmployeeLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mButtonPressed = 0;     //0 = employee button pressed
                signIn();
            }
        });

        mEmployerLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mButtonPressed = 1;     //1 = employer button pressed
                signIn();
            }
        });
    }

    private void initializeSignIn() {
        mCurrentUser = new UserObject();
        mCurrentUser.setEmail(mUser.getEmail());
        mCurrentUser.setName(mUser.getDisplayName());
        mCurrentUser.setPhotoUrl(mUser.getPhotoUrl().toString());
        mCurrentUser.setUid(mUser.getUid());
        Log.d(TAG, "onAuthStateChanged:signed_in:" + mUser.getUid());
        Toast.makeText(getApplicationContext(), "signed in successfully", Toast.LENGTH_SHORT).show();

        checkFirebaseDatabase();

    }

    private void checkFirebaseDatabase() {
//        mProgreeDialog.setMessage("Please Wait...");
//        mProgreeDialog.show();
        if(mButtonPressed == 0) {
            mEmployeesRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (mCurrentUser.getEmail() != null) {
                        String email = mCurrentUser.getEmail().replace('.', ',');
                        if (!dataSnapshot.hasChild(email)) {
//                    mProgreeDialog.dismiss();
//                        mCurrentUser.setCredits(0);
//                        mRefUsers.child(email).setValue(mCurrentUser);
                            //TODO call signup activity
                            Intent intent = new Intent(LoginActivity.this, SignUp.class);
                            startActivity(intent);
                        } else {
//                    mProgreeDialog.dismiss();
                            mCurrentUser = dataSnapshot.getValue(UserObject.class);
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    //fail the signin and signout the user
                    signOut();
                    needToSignOut = true;
                }
            });
        }else{
            mEmployersRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (mCurrentUser.getEmail() != null) {
                        String email = mCurrentUser.getEmail().replace('.', ',');
                        if (!dataSnapshot.hasChild(email)) {
//                    mProgreeDialog.dismiss();
//                        mCurrentUser.setCredits(0);
//                        mRefUsers.child(email).setValue(mCurrentUser);
                            //TODO call signup activity
                        } else {
//                    mProgreeDialog.dismiss();
                            mCurrentUser = dataSnapshot.getValue(UserObject.class);
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    //fail the signin and signout the user
                    signOut();
                    needToSignOut = true;
                }
            });
        }
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount account = result.getSignInAccount();
            firebaseAuthWithGoogle(account);
        } else {
            // Signed out, show unauthenticated UI.
            Toast.makeText(this, "Sign in failed by google", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//            supportFinishAfterTransition();
//            startActivity(intent);

        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
//
//                        Toast.makeText(getApplicationContext(), "signed in successfully 2", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
//                        supportFinishAfterTransition();
//                        startActivity(intent);
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // ...
                    }
                });
    }

    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        mGoogleApiClient.connect();
        mGoogleApiClient.registerConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
            @Override
            public void onConnected(@Nullable Bundle bundle) {

                FirebaseAuth.getInstance().signOut();
                if (mGoogleApiClient.isConnected()) {
                    Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
                        @Override
                        public void onResult(@NonNull Status status) {
                            if (status.isSuccess()) {
                            }
                        }
                    });
                }
            }

            @Override
            public void onConnectionSuspended(int i) {
                Toast.makeText(getApplicationContext(), "Some error occured. Please try Again", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

}
