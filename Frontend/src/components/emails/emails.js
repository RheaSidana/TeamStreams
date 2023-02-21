import React from "react";
import EmailCard from "./emailCard";

export default function Emails(props) {
    const { emails, handleDeleteEmail } = props;
  return (
            <div
              style={{
                display: "flex",
                alignItems: "center",
                flexWrap: "wrap",
              }}
            >
              {emails.length > 0
                ? emails.map((emailobj) => (
                    <EmailCard
                      emailObj={emailobj}
                      key={emailobj.id}
                      handleDeleteEmail={handleDeleteEmail}
                    />
                  ))
                : null}
            </div>
  );
}
