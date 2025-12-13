import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import { motion } from "framer-motion";
import useAuth from "@/auth/store";
import { useState } from "react";

function Userprofile() {
  const [isEditing, setIsEditing] = useState(false);
  const user = useAuth((state) => state.user);
  return (
    <div className="p-6 max-w-3xl mx-auto space-y-8">
      {/* Heading */}
      <motion.h1
        initial={{ opacity: 0, y: 10 }}
        animate={{ opacity: 1, y: 0 }}
        className="text-3xl font-bold text-center"
      >
        User Profile
      </motion.h1>

      {/* Profile Card */}
      <Card className="rounded-2xl shadow-md p-6">
        <CardHeader>
          <CardTitle className="text-xl font-semibold">
            Profile Information
          </CardTitle>
        </CardHeader>
        <CardContent className="space-y-6">
          {/* Avatar */}
          <div className="flex flex-col items-center gap-3">
            <Avatar className="w-28 h-28 border shadow-md">
              <AvatarImage src="https://api.dicebear.com/7.x/thumbs/svg?seed=user" />
              <AvatarFallback>U</AvatarFallback>
            </Avatar>
            <Button variant="outline" className="rounded-xl px-5">
              Change Picture
            </Button>
          </div>

          {/* User Details */}
          {!isEditing ? (
            <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
              <div className="space-y-2">
                <Label htmlFor="name">Full Name</Label>
                <Input
                  id="name"
                  value={user?.name}
                  readOnly
                  className="rounded-xl"
                />
              </div>

              <div className="space-y-2">
                <Label htmlFor="email">Email</Label>
                <Input
                  id="email"
                  value={user?.email}
                  readOnly
                  className="rounded-xl"
                />
              </div>

              <div className="space-y-2">
                <Label htmlFor="provider">Provider</Label>
                <Input
                  id="provider"
                  value={user?.provider}
                  readOnly
                  className="rounded-xl"
                />
              </div>

              <div className="space-y-2">
                <Label htmlFor="enabled">Enabled</Label>
                <Input
                  id="enabled"
                  value={user?.enabled ? "Yes" : "No"}
                  readOnly
                  className="rounded-xl"
                />
              </div>
            </div>
          ) : (
            <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
              <div className="space-y-2">
                <Label htmlFor="name">Full Name</Label>
                <Input
                  id="name"
                  value={user?.name}
                  onChange={(e) => {}}
                  className="rounded-xl"
                />
              </div>

              <div className="space-y-2">
                <Label htmlFor="email">Email</Label>
                <Input
                  id="email"
                  value={user?.email}
                  readOnly
                  className="rounded-xl"
                />
              </div>

              <div className="space-y-2">
                <Label htmlFor="provider">Provider</Label>
                <Input
                  id="provider"
                  value={user?.provider}
                  readOnly
                  className="rounded-xl"
                />
              </div>

              <div className="space-y-2">
                <Label htmlFor="enabled">Enabled</Label>
                <Input
                  id="enabled"
                  value={user?.enabled ? "Yes" : "No"}
                  readOnly
                  className="rounded-xl"
                />
              </div>
            </div>
          )}

          {!isEditing ? (
            <Button
              onClick={() => setIsEditing(true)}
              className="w-full rounded-2xl mt-4 text-lg"
            >
              Edit Profile
            </Button>
          ) : (
            <div className="flex gap-3 mt-4">
              <Button
                className="rounded-2xl w-full"
                onClick={() => setIsEditing(false)}
              >
                Cancel
              </Button>
              <Button
                className="rounded-2xl w-full"
                onClick={() => {
                  /* save handler */
                }}
              >
                Save
              </Button>
            </div>
          )}
        </CardContent>
      </Card>

      {/* Additional Section */}
      <Card className="rounded-2xl shadow-md p-6">
        <CardHeader>
          <CardTitle className="text-xl">Account Settings</CardTitle>
        </CardHeader>
        <CardContent className="space-y-4">
          <Button
            variant="outline"
            className="w-full rounded-xl py-3 text-base"
          >
            Change Password
          </Button>
          <Button
            variant="destructive"
            className="w-full rounded-xl py-3 text-base"
          >
            Delete Account
          </Button>
        </CardContent>
      </Card>
    </div>
  );
}

export default Userprofile;
