//
//  FeedbackScreen.swift
//  Verben
//

import SwiftUI
import shared

struct FeedbackScreen: View {
    @Environment(\.dismiss) var dismiss
    @EnvironmentObject var viewModel: ViewModel

    @State private var email: String = ""
    @State private var message: String = ""
    @State private var isLoading: Bool = false
    @State private var showSuccessAlert: Bool = false
    @State private var showErrorAlert: Bool = false
    @State private var validationError: String? = nil

    var body: some View {
        ScrollView {
            VStack(alignment: .leading, spacing: 16) {
                Text(Texts.feedbackSubtitle)
                    .font(.subheadline)
                    .foregroundColor(Colors.darkWhiteLightBlackColor)
                    .padding(.bottom, 4)

                VStack(alignment: .leading, spacing: 6) {
                    Text(Texts.feedbackEmailHint)
                        .font(.caption)
                        .foregroundColor(.gray)

                    TextField(Texts.feedbackEmailHint, text: $email)
                        .keyboardType(.emailAddress)
                        .autocapitalization(.none)
                        .disableAutocorrection(true)
                        .padding(.horizontal, 12)
                        .frame(height: 48)
                        .background(Color(UIColor.secondarySystemBackground))
                        .clipShape(RoundedRectangle(cornerRadius: 12))
                        .overlay(
                            RoundedRectangle(cornerRadius: 12)
                                .stroke(Color.gray.opacity(0.3), lineWidth: 1)
                        )
                }

                VStack(alignment: .leading, spacing: 6) {
                    Text(Texts.feedbackMessageHint)
                        .font(.caption)
                        .foregroundColor(.gray)

                    TextEditor(text: $message)
                        .frame(minHeight: 140)
                        .padding(8)
                        .background(Color(UIColor.secondarySystemBackground))
                        .clipShape(RoundedRectangle(cornerRadius: 12))
                        .overlay(
                            RoundedRectangle(cornerRadius: 12)
                                .stroke(Color.gray.opacity(0.3), lineWidth: 1)
                        )
                }

                if let validationError = validationError {
                    Text(validationError)
                        .font(.caption)
                        .foregroundColor(.red)
                }

                Button(action: submitFeedback) {
                    HStack {
                        Spacer()
                        if isLoading {
                            ProgressView()
                                .progressViewStyle(CircularProgressViewStyle(tint: .black))
                        } else {
                            Text(Texts.feedbackSend)
                                .font(.headline)
                                .foregroundColor(.black)
                        }
                        Spacer()
                    }
                    .frame(height: 48)
                    .background(Colors.primaryColor)
                    .clipShape(RoundedRectangle(cornerRadius: 12))
                }
                .disabled(isLoading)
                .padding(.top, 8)
            }
            .padding(20)
            .background(Colors.primaryLightColor.opacity(0.35))
            .clipShape(RoundedRectangle(cornerRadius: Size.cornerRadius))
            .overlay(
                RoundedRectangle(cornerRadius: Size.cornerRadius)
                    .stroke(Color.gray.opacity(0.4), lineWidth: 1)
            )
            .padding(.horizontal)
            .padding(.top, 16)
        }
        .navigationTitle(Texts.feedbackTitle)
        .navigationBarTitleDisplayMode(.inline)
        .alert(Texts.feedbackSuccess, isPresented: $showSuccessAlert) {
            Button(Texts.ok) {
                dismiss()
            }
        }
        .alert(Texts.feedbackError, isPresented: $showErrorAlert) {
            Button(Texts.ok, role: .cancel) { }
        }
    }

    private func submitFeedback() {
        let trimmedEmail = email.trimmingCharacters(in: .whitespacesAndNewlines)
        let trimmedMessage = message.trimmingCharacters(in: .whitespacesAndNewlines)

        let emailRegex = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}"
        let emailPredicate = NSPredicate(format: "SELF MATCHES %@", emailRegex)

        if !trimmedEmail.isEmpty && !emailPredicate.evaluate(with: trimmedEmail) {
            validationError = Texts.feedbackInvalidEmail
            return
        }

        if trimmedMessage.isEmpty {
            validationError = Texts.feedbackEmptyMessage
            return
        }

        validationError = nil
        isLoading = true

        Task {
            let success = await viewModel.sendFeedback(email: trimmedEmail, message: trimmedMessage)
            isLoading = false
            if success {
                showSuccessAlert = true
            } else {
                showErrorAlert = true
            }
        }
    }
}
